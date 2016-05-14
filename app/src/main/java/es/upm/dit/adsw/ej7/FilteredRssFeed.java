package es.upm.dit.adsw.ej7;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.adsw.ej7.rss.RssContent;

/**
 * RSS items filtrados.
 * Los que contienen todas las palabras de busqueda.
 */
public class FilteredRssFeed {
    private static final List<RssContent.EntryRss> ITEMS = new ArrayList<>();

    private static String[] filters = new String[0];

    /**
     * Resetea la lista.
     * Elimina los item anteriores y establece nuevos filtros.
     * @param words lista de palabras filtro: deben aparecer.
     */
    public static void reset(String words) {
        if (words == null || words.isEmpty())
            filters = new String[0];
        else
            filters = words.toLowerCase().split(" ");
        ITEMS.clear();
    }

    /**
     * Incorpora un nuevo item si contiene todas las palabras filtro.
     * @param item nuevo item a intentar
     */
    public static void add(RssContent.EntryRss item) {
        String title = item.title.toLowerCase();
        for (String filter: filters) {
            if (!title.contains(filter))
                return;
        }
        ITEMS.add(item);
    }

    /**
     * Getter.
     * @return items que cumplen con los requisitos.
     */
    public static List<RssContent.EntryRss> getEntries() {
        return ITEMS;
    }
}


