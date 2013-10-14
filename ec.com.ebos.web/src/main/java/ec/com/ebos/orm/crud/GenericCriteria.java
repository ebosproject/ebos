package ec.com.ebos.orm.crud;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertyExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;

import ec.com.ebos.root.model.Entidad;
import ec.com.ebos.util.ObjectUtils;

/**
 * Criteria generico para cualquier {@link Entidad}
 * @param <T>
 */
public class GenericCriteria<T> implements Serializable {

	private static final long serialVersionUID = -6088186846547455010L;

	private Class<T> clazz;
	private final CriteriaImpl impl;
	private final Criteria criteria;
	private boolean changed = false;
	private Map<String, String> aliasMap = new HashMap<String, String>(0);

	protected GenericCriteria(Class<T> clazz, CriteriaImpl impl, Criteria criteria) {
		this.clazz = clazz;
		this.impl = impl;
		this.criteria = criteria;
	}

	protected GenericCriteria(Class<T> clazz) {
		this.clazz = clazz;
		this.impl = new CriteriaImpl(this.clazz.getName(), null);
		this.criteria = this.impl;
	}

	protected GenericCriteria(Class<T> clazz, String alias) {
		this.clazz = clazz;
		this.impl = new CriteriaImpl(this.clazz.getName(), alias, null);
		this.criteria = this.impl;
	}

	public static <T> GenericCriteria<T> forClass(Class<T> clazz) {
		return new GenericCriteria<T>(clazz);
	}

	public static <T> GenericCriteria<T> forClass(Class<T> clazz, String alias) {
		return new GenericCriteria<T>(clazz, alias);
	}

	public DetachedCriteria getCriteria() {
		return new InnerDetachedCriteria(impl, criteria);
	}

	private static class InnerDetachedCriteria extends DetachedCriteria {
		private static final long serialVersionUID = 1L;
		protected InnerDetachedCriteria(CriteriaImpl impl, Criteria criteria) {
			super(impl, criteria);
		}
	}

	////////////////// Proxy methods ////////////////////

	public GenericCriteria<T> add(Criterion criterion) {
		criteria.add(criterion);
		return this;
	}

	public GenericCriteria<T> addOrder(Order order) {
		criteria.addOrder(order);
		return this;
	}

	public GenericCriteria<T> addOrderAsc(String propertyName) {
		criteria.addOrder(Order.asc(propertyName));
		return this;
	}

	public GenericCriteria<T> addOrderDesc(String propertyName) {
		criteria.addOrder(Order.desc(propertyName));
		return this;
	}

	public GenericCriteria<T> addOrderSql(String sqlFormula) {
		criteria.addOrder(OrderBySqlFormula.sqlFormula(sqlFormula));
		return this;
	}

	public GenericCriteria<T> createAlias(String associationPath, String alias, int joinType)
			throws HibernateException {
		if (!StringUtils.equals(aliasMap.get(associationPath), alias)) {
			aliasMap.put(associationPath, alias);
			criteria.createAlias(associationPath, alias, joinType);
		}
		return this;
	}

	public GenericCriteria<T> createAlias(String associationPath, String alias)
			throws HibernateException {
		return createAlias(associationPath, alias, CriteriaSpecification.INNER_JOIN);
	}

	public <V> GenericCriteria<V> createCriteria(Class<V> clazz, String associationPath, String alias)
			throws HibernateException {
		Criteria newCriteria = criteria.createCriteria(associationPath, alias);
		return new GenericCriteria<V>(clazz, impl, newCriteria);
	}

	public <V> GenericCriteria<V> createCriteria(Class<V> clazz, String associationPath)
			throws HibernateException {
		Criteria newCriteria = criteria.createCriteria(associationPath);
		return new GenericCriteria<V>(clazz, impl, newCriteria);
	}

	public String getAlias() {
		return criteria.getAlias();
	}

	public Criteria getExecutableCriteria(Session session) {
		impl.setSession(( SessionImplementor ) session);
		return impl;
	}

	public GenericCriteria<T> setFetchMode(String associationPath, FetchMode mode) throws HibernateException {
		criteria.setFetchMode(associationPath, mode);
		return this;
	}

	public GenericCriteria<T> setProjection(Projection projection) {
		criteria.setProjection(projection);
		return this;
	}

	public GenericCriteria<T> setResultTransformer(ResultTransformer resultTransformer) {
		criteria.setResultTransformer(resultTransformer);
		return this;
	}

	public String toString() {
		return criteria.toString();
	}

	////////////////// Proxy restriction methods ////////////////////

	/**
	 * Agrega filtros exists con los GenericCriteria dados
	 * @author Eduardo Plua Alay
	 */
	public GenericCriteria<T> addExists(GenericCriteria<?>... subCriterias) {
		Disjunction or = Restrictions.disjunction();
		changed = false;
		if (!ObjectUtils.isEmpty(subCriterias)) {
			for (GenericCriteria<?> subCriteria : subCriterias) {
				if (subCriteria != null) {
//					subCriteria.setProjection(Projections.id());
					subCriteria.setProjection(Projections.sqlProjection("1", null, null));
					or.add(Subqueries.exists(subCriteria.getCriteria()));
					changed = true;
				}
			}
			if (changed) {
				criteria.add(or);
			}
		}
		return this;
	}

	/**
	 * Adds a SQL restriction
	 */
	public GenericCriteria<T> addSql(String sql) {
		criteria.add(Restrictions.sqlRestriction(sql));
		return this;
	}

	/**
	 * Adds a SQL restriction with given JDBC param
	 */
	public GenericCriteria<T> addSql(String sql, Object value, Type type) {
		criteria.add(Restrictions.sqlRestriction(sql, value, type));
		return this;
	}

	/**
	 * Adds a SQL restriction with given JDBC params
	 */
	public GenericCriteria<T> addSql(String sql, Object[] values, Type[] types) {
		criteria.add(Restrictions.sqlRestriction(sql, values, types));
		return this;
	}

	/**
	 * Adds an "equal" constraint to the named property,
	 * not ignoring case by default
	 */
	public GenericCriteria<T> addEquals(String propertyName, Object value) {
		return addEquals(propertyName, value, false);
	}
	
	/**
	 * Adds an "equal" if only value == true
	 */
	public GenericCriteria<T> addEqualsIsTrue(String propertyName, boolean value) {
		return value ? addEquals(propertyName, value, false) : this;
	}

	public GenericCriteria<T> addLT(String propertyName, Object value) {
		criteria.add(Restrictions.lt(propertyName, value));
		return this;
	}

	public GenericCriteria<T> addGT(String propertyName, Object value) {
		criteria.add(Restrictions.gt(propertyName, value));
		return this;
	}

	public GenericCriteria<T> addLE(String propertyName, Object value) {
		criteria.add(Restrictions.le(propertyName, value));
		return this;
	}

	public GenericCriteria<T> addGE(String propertyName, Object value) {
		criteria.add(Restrictions.ge(propertyName, value));
		return this;
	}
	//	public <P, V extends P> GenericCriteria<T> addSafeEquals(P property, V value) {
//		Argument<P> arg = Lambda.argument(property);
//		return addEquals(arg.getInkvokedPropertyName(), value, false);
//	}

	/**
	 * Adds an "equal" constraint to the named property,
	 * optionally ignoring case
	 */
	public GenericCriteria<T> addEquals(String propertyName, Object value, boolean ignoreCase) {
		if (ignoreCase) {
			criteria.add(Restrictions.eq(propertyName, value).ignoreCase());
		} else {
			criteria.add(Restrictions.eq(propertyName, value));
		}
		return this;
	}

	/**
	 * Adds an "equal" constraint to two properties
	 */
	public GenericCriteria<T> addEqualsProperty(String propertyName, String otherPropertyName) {
		criteria.add(Restrictions.eqProperty(propertyName, otherPropertyName));
		return this;
	}
	
	
	
	/**
	 * Adds an "like" constraint to two properties
	 */
	public GenericCriteria<T> addLikeProperty(String propertyName, String otherPropertyName) {
		criteria.add(new InnerPropertyExpression(propertyName, otherPropertyName, "like"));
		return this;
	}
	
	/**
	 * Superclase para comparacion entre dos propiedades
	 * @author <a href="mailto:eduardo.plua@gmail.com">Eduardo Plua Alay</a>
	 *
	 */
	private static class InnerPropertyExpression extends PropertyExpression {
		protected InnerPropertyExpression(String propertyName,
				String otherPropertyName, String op) {
			super(propertyName, otherPropertyName, op);
		}
		private static final long serialVersionUID = 2L;
		
	}

	public GenericCriteria<T> addIsNull(String propertyName) {
		criteria.add(Restrictions.isNull(propertyName));
		return this;
	}

	/**
	 * Adds a disjunction <code>(property=value or property is null)</code>.
	 * If value==null, only the "is null" constraint is added.
	 */
	public GenericCriteria<T> addEqualsOrIsNull(String propertyName, Object value) {
		Disjunction or = Restrictions.disjunction();
		if (value != null) {
			or.add(Restrictions.eq(propertyName, value));
		}
		or.add(Restrictions.isNull(propertyName));
		criteria.add(or);
		return this;
	}

	/**
	 * Adds a disjunction <code>(property<>value or property is null)</code>.
	 * If value==null, only the "is null" constraint is added.
	 */
	public GenericCriteria<T> addNotEqualsOrIsNull(String propertyName, Object value) {
		Disjunction or = Restrictions.disjunction();
		if (value != null) {
			or.add(Restrictions.ne(propertyName, value));
		}
		or.add(Restrictions.isNull(propertyName));
		criteria.add(or);
		return this;
	}

	public GenericCriteria<T> addIsNotNull(String propertyName) {
		criteria.add(Restrictions.isNotNull(propertyName));
		return this;
	}

	/**
	 * Adds a "not equal" constraint to the named property
	 */
	public GenericCriteria<T> addNotEquals(String propertyName, Object value) {
		criteria.add(Restrictions.ne(propertyName, value));
		return this;
	}

	/**
	 * Adds a "not equal" constraint to the named property, if value != null
	 */
	public GenericCriteria<T> addNotEqualsIfNotNull(String propertyName, Object value) {
		changed = false;
		if (value != null) {
			criteria.add(Restrictions.ne(propertyName, value));
			changed = true;
		}
		return this;
	}

	/**
	 * Adds a "like" constraint to the named property, if value != empty
	 */
	public GenericCriteria<T> addLike(String propertyName, String value) {
		changed = false;
		if (StringUtils.isNotEmpty(value)) {
			criteria.add(Restrictions.ilike(propertyName, value, MatchMode.ANYWHERE));
			changed = true;
		}
		return this;
	}
	/**
	 * Adds a "like" constraint to the named property, if value !=null and != empty
	 */
	public GenericCriteria<T> addLikeIfNotNull(String propertyName, String value){
		changed = false;
		if (value != null && StringUtils.isNotEmpty(value)) {
			criteria.add(Restrictions.ilike(propertyName, value, MatchMode.ANYWHERE));
			changed = true;
		}		
		return this;
	}

	/**
	 * Adds a "like" constraint to the named property,
	 * for each of the given values or tokens, split using " " as separator,
	 * if values != empty and each value != empty
	 */
	public GenericCriteria<T> addLikeTokens(String propertyName, String values) {
		return addLikeTokensSeparator(propertyName, values, " ");
	}

	/**
	 * Adds a "like" constraint to the named property,
	 * for each of the given values or tokens, split using the given separator,
	 * if values != empty and each value != empty
	 */
	public GenericCriteria<T> addLikeTokensSeparator(String propertyName, String values, String separator) {
		changed = false;
		if (StringUtils.isNotBlank(values)) {
			String[] tokens = values.split(separator);
			if (!ObjectUtils.isEmpty(tokens)) {
				for (String value : tokens) {
					if (StringUtils.isNotBlank(value)) {
						criteria.add(Restrictions.ilike(propertyName, value, MatchMode.ANYWHERE));
						changed = true;
					}
				}
			}
		}
		return this;
	}

	public GenericCriteria<T> addStartLike(String propertyName, String value) {
		changed = false;
		if (StringUtils.isNotBlank(value)) {
			criteria.add(Restrictions.ilike(propertyName, value, MatchMode.START));
			changed = true;
		}
		return this;
	}

	public GenericCriteria<T> addNotStartLike(String propertyName, String value) {
		changed = false;
		if (StringUtils.isNotBlank(value)) {
			criteria.add(Restrictions.not(Restrictions.ilike(propertyName, value, MatchMode.START)));
			changed = true;
		}
		return this;
	}

	/**
	 * Adds a "like" constraint to each of the named properties, as a disjunction, if value != empty
	 */
	public GenericCriteria<T> addValueLikeProperties(String value, String... propertyNames) {
		changed = false;
		if (StringUtils.isNotBlank(value) && !ObjectUtils.isEmpty(propertyNames)) {
			Disjunction or = Restrictions.disjunction();
			for (String propertyName : propertyNames) {
				or.add(Restrictions.ilike(propertyName, value, MatchMode.ANYWHERE));
			}
			criteria.add(or);
			changed = true;
		}
		return this;
	}

	public GenericCriteria<T> addPropertyLikeValues(String propertyName, String... values) {
		changed = false;
		if (StringUtils.isNotBlank(propertyName) && !ObjectUtils.isEmpty(values)) {
			Disjunction or = Restrictions.disjunction();
			for (String value : values) {
				or.add(Restrictions.ilike(propertyName, value, MatchMode.ANYWHERE));
			}
			criteria.add(or);
			changed = true;
		}
		return this;
	}
	/**
	 * Adds a "like" constraint to each of the named properties, as a disjunction,
	 * for each of the given values or tokens, split using " " as separator,
	 * if values != empty and each value != empty
	 */
	public GenericCriteria<T> addMultiLikeTokens(String values, String... propertyNames) {
		return addMultiLikeTokensSeparator(values, " ", propertyNames);
	}

	/**
	 * Adds a "like" constraint to each of the named properties, as a disjunction,
	 * for each of the given values or tokens, split using the given separator,
	 * if values != empty and each value != empty
	 */
	public GenericCriteria<T> addMultiLikeTokensSeparator(String values, String separator, String... propertyNames) {
		changed = false;
		if (StringUtils.isNotBlank(values)) {
			String[] tokens = values.split(separator);
			if (!ObjectUtils.isEmpty(tokens)) {
				for (String value : tokens) {
					if (StringUtils.isNotBlank(value) && !ObjectUtils.isEmpty(propertyNames)) {
						Disjunction or = Restrictions.disjunction();
						for (String propertyName : propertyNames) {
							or.add(Restrictions.ilike(propertyName, value, MatchMode.ANYWHERE));
						}
						criteria.add(or);
						changed = true;
					}
				}
			}
		}
		return this;
	}

	/**
	 * Adds an "equals" constraint to each of the named properties, as a disjunction, if value != null
	 */
	public GenericCriteria<T> addMultiEquals(Object value, String... propertyNames) {
		changed = false;
		if (value != null && !ObjectUtils.isEmpty(propertyNames)) {
			Disjunction or = Restrictions.disjunction();
			for (String propertyName : propertyNames) {
				or.add(Restrictions.eq(propertyName, value));
			}
			criteria.add(or);
			changed = true;
		}
		return this;
	}

	/**
	 * Adds an "equal" constraint to the named property, if value != null
	 */
	public GenericCriteria<T> addEqualsIfNotNull(String propertyName, Object value) {
		changed = false;
		if (value != null) {
			criteria.add(Restrictions.eq(propertyName, value));
			changed = true;
		}
		return this;
	}

	/**
	 * Adds an "equal" constraint to the named property, if value != null.
	 * Adds an "is null" constraint to the named property, otherwise.
	 */
	public GenericCriteria<T> addEqualsNullSafe(String propertyName, Object value) {
		if (value != null) {
			criteria.add(Restrictions.eq(propertyName, value));
		} else {
			criteria.add(Restrictions.isNull(propertyName));
		}
		return this;
	}

	public <I> GenericCriteria<T> addEqualsIfNotNull(I clazz,  String propertyName, I value) {
		changed = false;
		if (value != null) {
			criteria.add(Restrictions.eq(propertyName, value));
			changed = true;
		}
		return this;
	}

	/**
	 * Adds an "equal" constraint to the named property, if value != null && value != 0
	 */
	public GenericCriteria<T> addEqualsIfNotZero(String propertyName, Number value) {
		changed = false;
		if (value != null && value.longValue() != 0) {
			criteria.add(Restrictions.eq(propertyName, value));
			changed = true;
		}
		return this;
	}

	/**
	 * Adds an "equal" constraint to the named property, if value != null && value != empty,
	 * ignoring case by default
	 */
	public GenericCriteria<T> addEqualsIfNotEmpty(String propertyName, String value) {
		return addEqualsIfNotEmpty(propertyName, value, true);
	}

	/**
	 * Adds an "equal" constraint to the named property, if value != null && value != empty,
	 * optionally ignoring case
	 */
	public GenericCriteria<T> addEqualsIfNotEmpty(String propertyName, String value, boolean ignoreCase) {
		changed = false;
		if (StringUtils.isNotEmpty(value)) {
			if (ignoreCase)
				criteria.add(Restrictions.eq(propertyName, value).ignoreCase());
			else
				criteria.add(Restrictions.eq(propertyName, value));
			changed = true;
		}
		return this;
	}

	private GenericCriteria<T> addBetweenPrv(String propertyName, Object startValue, Object endValue) {
		if (startValue != null && endValue != null) {
			criteria.add(Restrictions.between(propertyName, startValue, endValue));
		}
		return this;
	}

	/**
	 * Adds a "between" constraint to the named property,
	 * if startValue != null && endValue != null
	 */
	public GenericCriteria<T> addBetween(String propertyName, Date startValue, Date endValue) {
		if (startValue != null && endValue != null) {
	        Calendar c = Calendar.getInstance();
	        c.setLenient(false);
	        c.setTime(endValue);
	        c.set(Calendar.HOUR_OF_DAY, 23);
	        c.set(Calendar.MINUTE, 59);
	        c.set(Calendar.SECOND, 59);
	        endValue = c.getTime();
			criteria.add(Restrictions.between(propertyName, startValue, endValue));
		}
		return this;
	}

	/**
	 * Adds a "between" constraint to the named property,
	 * if startValue != null && endValue != null
	 */
	public GenericCriteria<T> addBetween(String propertyName, String startValue, String endValue) {
		return addBetweenPrv(propertyName, startValue, endValue);
	}

	/**
	 * Adds a "between" constraint to the named property,
	 * if startValue != null && endValue != null
	 */
	public GenericCriteria<T> addBetween(String propertyName, Number startValue, Number endValue) {
		return addBetweenPrv(propertyName, startValue, endValue);
	}

	/**
	 * Adds a <code>startPropertyName <= value and value <= endPropertyName</code> constraint
	 */
	public GenericCriteria<T> addBetweenProperty(String startPropertyName, String endPropertyName, Object value) {
		if (value != null) {
			criteria.add(Restrictions.le(startPropertyName, value));
			criteria.add(Restrictions.ge(endPropertyName, value));
		}
		return this;
	}

	/**
	 * Adds a range overlap constraint, if startValue != null and endValue != null<br>
	 * <strong>Explanation:</strong><br>
	 * Let condition A mean TableRange completely after GivenRange (true if <code>startProperty > endValue</code>)<br>
	 * Let condition B mean GivenRange completely after TableRange (true if <code>endProperty < startValue</code>)<br>
	 * Then overlap exists if Neither A Nor B is true
	 * (if one range is neither completely after the other,
	 * nor completely before the other, then they must overlap)<br>
	 * But from De Morgan's law: <code>Not(A Or B) <=> Not(A) And Not(B)</code><br>
	 * Then overlap exists if: <code>(startProperty <= endValue) And (endProperty >= startValue)</code>
	 * @see http://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap
	 */
	public GenericCriteria<T> addRangeOverlap(String startPropertyName, String endPropertyName, Object startValue, Object endValue) {
		changed = false;
		if (startValue != null && endValue != null) {
			criteria.add(Restrictions.le(startPropertyName, endValue));
			criteria.add(Restrictions.ge(endPropertyName, startValue));
			changed = true;
		}
		return this;
	}

	/**
	 * Adds an "in" constraint to the named property,
	 * if values != null and values is not empty
	 */
	public GenericCriteria<T> addIn(String propertyName, Object... values) {
		changed = false;
		if (values != null && values.length != 0) {
			criteria.add(Restrictions.in(propertyName, values));
			changed = true;
		}
		return this;
	}

	public GenericCriteria<T> addNotInValues(String propertyName, Object... values) {
		changed = false;
		if (values != null && values.length != 0) {
			criteria.add(Restrictions.not(Restrictions.in(propertyName, values)));
			changed = true;
		}
		return this;
	}
	
	/**
	 * Adds an "in" constraint to the named property,
	 * if col != null and col is not empty
	 */
	public GenericCriteria<T> addInCollection(String propertyName, Collection<?> col) {
		changed = false;
		if (!ObjectUtils.isEmpty(col)) {
			criteria.add(Restrictions.in(propertyName, col));
			changed = true;
		}
		return this;
	}

	public GenericCriteria<T> addNotInCollection(String propertyName, Collection<?> col) {
		changed = false;
		if (!ObjectUtils.isEmpty(col)) {
			criteria.add(Restrictions.not(Restrictions.in(propertyName, col)));
			changed = true;
		}
		return this;
	}

	/**
	 * Adds a disjunction <code>(property in(col) or property is null)</code>.
	 * If col is null or empty, only the "is null" constraint is added.
	 */
	public GenericCriteria<T> addInCollectionOrIsNull(String propertyName, Collection<?> col) {
		Disjunction or = Restrictions.disjunction();
		if (!ObjectUtils.isEmpty(col)) {
			or.add(Restrictions.in(propertyName, col));
		}
		or.add(Restrictions.isNull(propertyName));
		criteria.add(or);
		return this;
	}

	/**
	 * Adds an "in" constraint to each of the named properties,
	 * as a disjunction, if col != null and col is not empty
	 */
	public GenericCriteria<T> addMultiInCollection(Collection<?> col, String... propertyNames) {
		changed = false;
		if (!ObjectUtils.isEmpty(col) && !ObjectUtils.isEmpty(propertyNames)) {
			Disjunction or = Restrictions.disjunction();
			for (String propertyName : propertyNames) {
				or.add(Restrictions.in(propertyName, col));
			}
			criteria.add(or);
			changed = true;
		}
		return this;
	}

	@Deprecated
	public GenericCriteria<T> addJoins(String... associationPaths) {
		if (associationPaths != null) {
			for (String associationPath : associationPaths) {
				criteria.setFetchMode(associationPath, FetchMode.JOIN);
			}
		}
		return this;
	}

	/**
	 * Usage:<br>
	 * <code>addAliasedJoins("path1.property1", "path2.property2@alias2", "property3", ...)</code><br>
	 * Generates:<br>
	 * <code>createAlias("path1.property1", "property1")<br>
	 * createAlias("path2.property2", "alias2")<br>
	 * createAlias("property3", "property3")<br>
	 * ...<br>
	 * addJoins("path1.property1", "path2.property2@alias2", "property3", ...)</code>
	 * @param associationPaths
	 * @return
	 */
	private GenericCriteria<T> addAliasedJoins(int joinType, String... associationPaths) {
		if (associationPaths != null) {
			for (String associationPath : associationPaths) {
				String[] tokens = associationPath.split("@");
				if (associationPath.length() == 0 || tokens.length > 2) {
					throw new IllegalArgumentException();
				}
				String path = tokens[0];
				String alias = tokens[tokens.length - 1];
				if (tokens.length == 1) {
					tokens = alias.split("\\.");
					alias = tokens[tokens.length - 1];
				}
				createAlias(path, alias, joinType);
				criteria.setFetchMode(path, FetchMode.JOIN);
			}
		}
		return this;
	}

	/**
	 * Usage:<br>
	 * <code>addAliasedJoins("path1.property1", "path2.property2@alias2", "property3", ...)</code><br>
	 * Generates:<br>
	 * <code>createAlias("path1.property1", "property1", CriteriaSpecification.INNER_JOIN)<br>
	 * createAlias("path2.property2", "alias2", CriteriaSpecification.INNER_JOIN)<br>
	 * createAlias("property3", "property3", CriteriaSpecification.INNER_JOIN)<br>
	 * ...<br>
	 * addJoins("path1.property1", "path2.property2@alias2", "property3", ...)</code>
	 * @param associationPaths
	 * @return
	 */
	public GenericCriteria<T> addAliasedJoins(String... associationPaths) {
		return addAliasedJoins(CriteriaSpecification.INNER_JOIN, associationPaths);
	}

	/**
	 * Usage:<br>
	 * <code>addAliasedLeftJoins("path1.property1", "path2.property2@alias2", "property3", ...)</code><br>
	 * Generates:<br>
	 * <code>createAlias("path1.property1", "property1", CriteriaSpecification.LEFT_JOIN)<br>
	 * createAlias("path2.property2", "alias2",CriteriaSpecification.LEFT_JOIN)<br>
	 * createAlias("property3", "property3", CriteriaSpecification.LEFT_JOIN)<br>
	 * ...<br>
	 * addJoins("path1.property1", "path2.property2@alias2", "property3", ...)</code>
	 * @param associationPaths
	 * @return
	 */
	public GenericCriteria<T> addAliasedLeftJoins(String... associationPaths) {
		return addAliasedJoins(CriteriaSpecification.LEFT_JOIN, associationPaths);
	}

	/**
	 * Tells if this criteria has just been changed by the last addEquals* method
	 * @return
	 * @see #addEqualsIfNotNull(String, Object)
	 * @see #addEqualsIfNotZero(String, Number)
	 * @see #addEqualsIfNotEmpty(String, String)
	 */
	public boolean isChanged() {
		return changed;
	}

}
