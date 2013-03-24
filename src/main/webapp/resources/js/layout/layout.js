PrimeFaces.widget.Layout = PrimeFaces.widget.BaseWidget.extend({
    /**
     * Initializes the widget.
     *
     * @param {object} cfg The widget configuration.
     */
    init:function (cfg) {
        this._super(cfg);
        this.cfg = cfg;
        this.id = cfg.id;
        this.jqTarget = $(cfg.forTarget);

        var _self = this;

        if (this.jqTarget.is(':visible')) {
            this.createLayout();
        } else {
            var hiddenParent = this.jqTarget.parents('.ui-hidden-container:first');
            var hiddenParentWidget = hiddenParent.data('widget');

            if (hiddenParentWidget) {
                hiddenParentWidget.addOnshowHandler(function () {
                    return _self.createLayout();
                });
            }
        }
    },

    createLayout:function () {
        // create layout
        this.layout = this.jqTarget.layout(this.cfg.options);

        // bind "open", "close" and "resize" events
        this.bindEvents(this.jqTarget);
    },
    
    bindEvents:function(parent) {
        var _self = this;

        // bind events
        parent.find(".ui-layout-pane")
            .bind("layoutpaneonopen",function () {
                var behavior = _self.cfg.behaviors ? _self.cfg.behaviors['open'] : null;
                if (behavior) {
                    var combinedPosition = $(this).data('combinedposition');
                    var ext = {
                        params:[
                            {name:_self.id + '_pane', value:combinedPosition}
                        ]
                    };
    
                    behavior.call(_self, combinedPosition, ext);
                }
            }).bind("layoutpaneonclose",function () {
                var behavior = _self.cfg.behaviors ? _self.cfg.behaviors['close'] : null;
                if (behavior) {
                    var combinedPosition = $(this).data('combinedposition');
                    var ext = {
                        params:[
                            {name:_self.id + '_pane', value:combinedPosition}
                        ]
                    };
    
                    behavior.call(_self, combinedPosition, ext);
                }
            }).bind("layoutpaneonresize", function () {
                var layoutPane = $(this).data("layoutPane");
    
                if (!layoutPane.state.isClosed && !layoutPane.state.isHidden) {
                    var behavior = _self.cfg.behaviors ? _self.cfg.behaviors['resize'] : null;
                    if (behavior) {
                        var combinedPosition = $(this).data('combinedposition');
                        var ext = {
                            params:[
                                {name:_self.id + '_pane', value:combinedPosition},
                                {name:_self.id + '_width', value:layoutPane.state.innerWidth},
                                {name:_self.id + '_height', value:layoutPane.state.innerHeight}
                            ]
                        };
    
                        behavior.call(_self, combinedPosition, ext);
                    }
                }
            });        
    },

    toggle:function (pane) {
        this.jqTarget.find(".ui-layout-pane").
            each(function() {
                var combinedPosition = $(this).data('combinedposition');
                if (combinedPosition && combinedPosition === pane) {
                    $(this).trigger("layoutpanetoggle");
                    return false;
                }
            });
    },

    close:function (pane) {
        this.jqTarget.find(".ui-layout-pane").
            each(function() {
                var combinedPosition = $(this).data('combinedposition');
                if (combinedPosition && combinedPosition === pane) {
                    $(this).trigger("layoutpaneclose");
                    return false;
                }
            });
    },

    open:function (pane) {
        this.jqTarget.find(".ui-layout-pane").
            each(function() {
                var combinedPosition = $(this).data('combinedposition');
                if (combinedPosition && combinedPosition === pane) {
                    $(this).trigger("layoutpaneopen");
                    return false;
                }
            });
    },

    sizePane:function (pane, size) {
        this.jqTarget.find(".ui-layout-pane").
            each(function() {
                var combinedPosition = $(this).data('combinedposition');
                if (combinedPosition && combinedPosition === pane) {
                    $(this).trigger("layoutpanesize", [size]);
                    return false;
                }
            });
    }  
});
