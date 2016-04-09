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
    public static final String GET_INGREDIENTS = "select I.Iid,I.ingredientName,D.IAmount,D.Uid from ingredient I, ingredientdetails D where I.Iid = D.Iid and D.Rid = ?";

    public static final String GET_NUTRITIONS = "select N.Nid,N.NName,D.NAmount,D.Uid from nutrition N, nutritiondetails D where N.Nid = D.Nid and D.Rid = ?";

    public static final String GET_COMMENTS = "select CRating, Comment from comment where Rid =?";

    public static final String WRITE_COMMENT = "insert into Comment(Rid,comment,crating) values (?,?,?)";

    public static final String GET_FLAVORS = "select * from Flavor";

    public static final String GET_RECIPES = "select * from Recipe";

    public static final String GET_RECIPE = "select * from Recipe where Rid = ?";

}

