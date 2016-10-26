package fr.kwizzy.waroflegions.rank;

public enum Rank
{

	RECRUE(0, "recrue", "§eRecrue", 0),
    ASPIRANT(1, "aspirant", "§bAspirant", 10),
    SENTINELLE(2, "sentinelle", "§bSentinelle", 20),
    DEFENSEUR(3, "defenseur", "§6Défenseur", 30),
    CHEVALIER(4, "chevalier", "§6Chevalier",40),
    CHAMPION(5, "champion", "§dChampion", 50),
    ASSASSIN(6, "assassin", "§dAssassin", 60),
    STRATEGE(7, "stratege", "§9Stratège", 70),
    BROYEUR(8, "broyeur", "§cBroyeur", 80),
    HERO(9, "hero", "§6★ §cHéro", 100);

	private int id;
	private String name;
	private String coloredName;
	private int level;

	Rank(int id, String name, String coloredName, int level)
    {
		this.id = id;
		this.name = name;
		this.coloredName = coloredName;
		this.level = level;
	}

	public int getId()
    {
		return id;
	}

	public String getName()
    {
		return name;
	}

	public String getColoredName()
    {
		return coloredName;
	}

	public int getLevel()
    {
		return level;
	}

	public static Rank getById(int i)
    {
		for (Rank r : Rank.values())
        {
			if (r.getId() == i)
				return r;
		}
		return RECRUE;
	}
}