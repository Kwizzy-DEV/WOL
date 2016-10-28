package fr.kwizzy.waroflegions.legion;

/**
 * Par Alexis le 24/10/2016.
 */
public enum Legion
{
    NEUTRAL("neutre", "§eNeutre", 0),
    ANA("ana", "§aAna", 1),
    KATA("kata", "§bKata", 2);

    private String legionName;
    private int id;
    private String prefix;

    Legion(String legionName, String prefix, int id)
    {
        this.legionName = legionName;
        this.id = id;
        this.prefix = prefix;
    }

    public String getLegionName()
    {
        return legionName;
    }

    public void setLegionName(String legionName)
    {
        this.legionName = legionName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public static Legion getById(int i)
    {
        for (Legion legion : Legion.values())
        {
            if(legion.getId() == i)
                return legion;
        }
        return NEUTRAL;
    }
}
