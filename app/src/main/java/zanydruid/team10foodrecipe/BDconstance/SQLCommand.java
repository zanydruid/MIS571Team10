package zanydruid.team10foodrecipe.BDconstance;

/**
 * Created by yizhu on 2/16/16.
 */
/**
 * SQL commands
 * Including select/delete/update/insert
 */


public abstract class SQLCommand
{
    // get the list of all units
    public static final String GET_UNITS = "select * from Unit";

    // get list of ingredients by a recipe id
    public static final String GET_RECIPE_BY_ID = "select Rid,Rname,time,ServePpl,Description,Source,picture from recipe where Rid = ?";

    public static final String GET_AVERAGE_RATING = "select avg(Crating) from comment where Rid = ?";

    public static final String GET_RECIPES = "select Rid, RName, Time, picture from Recipe";

    public static final String GET_INGREDIENTS = "select * from ingredient";

    public static final String GET_INGREDIENTS_BY_ID = "select I.Iid, I.IngredientName,D.Iamount, I.uid from ingredient I, ingredientDetails D where D.Iid = I.Iid and D.rid = ?";

    public static final String GET_NUTRITIONS_BY_ID = "select nutrition.Nid,nutrition.NName,sum(N.NAmount*I.IAmount) as [totalFacts],nutrition.Uid from nutrition, nutritiondetails N, ingredientdetails I where nutrition.Nid = N.Nid and N.Iid = I.Iid and Rid = ? group by nutrition.Nid";

    public static final String GET_COMMENTS = "select CRating, Comment from comment where Rid =?";

    public static final String WRITE_COMMENT = "insert into Comment(Rid,comment,crating) values (?,?,?)";

    public static final String GET_FLAVORS_By_ID = "select flavor.fname, d.famount from flavor, flavordetails d where d.fid = flavor.fid and d.rid = ?";

    public static final String GET_FLAVORS = "select * from flavor";

    public static final String INSERT_RECIPE = "insert into recipe (Rid,RName,Description,Time,ServePpl,Source,picture) values(?,?,?,?,?,?,?)";

    public static final String UPDATE_RECIPE = "update recipe set RName=?,Time=?,ServePpl=?,Description=?,Source=?,picture=? where rid=?";

    public static final String INSERT_FLAVORS = "insert into flavordetails (Fid, Rid, Famount) values(?,?,?)";

    public static final String INSERT_INGREDIENTS_BY_ID = "insert into ingredientdetails (Iid,Rid,IAmount) values(?,?,?)";
}

