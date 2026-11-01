package com.example.poster1019;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The one place that owns the app's movie data: all posters, the selection, and the favorites.
 */
public class PosterRepository {

    // Notified whenever favorites are added or removed (Favorite List, badge).
    public interface FavoritesListener{
        void onFavoritesChanged();
    }

    private static PosterRepository instance;

    private final List<Poster> allPosters;
    private final FavoritesStore favoritesStore;
    private final List<FavoritesListener> favoritesListeners = new ArrayList<>();

    // Creates the single instance on first use. It keeps the application context,
    // never an Activity: holding an Activity in a singleton would leak it.
    public static synchronized PosterRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PosterRepository(context.getApplicationContext());
        }
        return instance;
    }

    private PosterRepository(Context appContext) {
        allPosters = Collections.unmodifiableList(createPosters(appContext));
        favoritesStore = new FavoritesStore(appContext);

        // Forget saved favorites whose poster no longer exists in the app.
        List<String> validIds = new ArrayList<>();
        for (Poster poster : allPosters) {
            validIds.add(poster.id);
        }
        favoritesStore.retainOnly(validIds);
    }

    public List<Poster> getAllPosters(){
        return allPosters;
    }

    public Poster findById(String id){
        for(Poster posters:allPosters){
            if(posters.id.equals(id)){
                return posters;
            }
        }
        return null;
    }

    // deselects every poster
    public void clearSelection(){
        for(Poster poster : allPosters){
            poster.isSelected = false;
        }
    }

    //get favorite posters, in the order they were added
    public List<Poster> getFavorites(){
        List<Poster> favorites = new ArrayList<>();
        for (String id : favoritesStore.getIds()) {
            Poster poster = findById(id);
            if (poster != null) {
                favorites.add(poster);
            }
        }
        return favorites;
    }

    public int getFavoriteCount(){
        return favoritesStore.getIds().size();
    }

    // Adds posters to favorites, skipping ones already there.
    // Returns how many were new, so the page can show the right message.
    public int addFavorites(List<Poster> posters) {
        List<String> ids = new ArrayList<>();
        for (Poster poster : posters) {
            ids.add(poster.id);
        }
        int added = favoritesStore.addAll(ids);
        if (added > 0) {
            notifyFavoritesChanged();
        }
        return added;
    }

    // Removes one favorite. Returns true if it was a favorite.
    public boolean removeFavorite(String id) {
        boolean removed = favoritesStore.remove(id);
        if (removed) {
            notifyFavoritesChanged();
        }
        return removed;
    }

    // The listener (observer) pattern: pages register to hear about changes.
    // Whoever adds a listener must remove it again (onDestroyView / onDestroy).
    public void addFavoritesListener(FavoritesListener listener) {
        if (!favoritesListeners.contains(listener)) {
            favoritesListeners.add(listener);
        }
    }

    public void removeFavoritesListener(FavoritesListener listener) {
        favoritesListeners.remove(listener);
    }

    private void notifyFavoritesChanged() {
        for(FavoritesListener listener : new ArrayList<>(favoritesListeners)){
            listener.onFavoritesChanged();
        }
    }

    /**
     *
     * @param context
     * @return
     */
    private static List<Poster> createPosters(Context context) {
        List<Poster> posters = new ArrayList<>();
        Resources res = context.getResources();
        // Gets all the information for the poster's and stores info types as arrays
        String[] ids = res.getStringArray(R.array.ids);
        int[] images = {
                R.drawable.backrooms_poster,
                R.drawable.super_mario_galaxy_poster,
                R.drawable.hobbit_poster,
                R.drawable.goonies_poster,
                R.drawable.avengers_doomsday_poster,
                R.drawable.dune_poster,
                R.drawable.gatsby_poster,
                R.drawable.hp_deathly_hallows_poster,
                R.drawable.infinity_war_poster,
                R.drawable.superman_poster,
                R.drawable.sw_last_jedi_poster,
                R.drawable.wall_e_poster
        };
        String[] titles = res.getStringArray(R.array.titles);
        String[] ratings = res.getStringArray(R.array.ratings);
        String[] producers = res.getStringArray(R.array.producers);
        String[] descriptions = res.getStringArray(R.array.descriptions);
        // Creates all the posters with the appropriate movie details and adds to the list
        for (int i = 0; i < ids.length; i++) {
            Poster poster = new Poster();
            poster.id = ids[i];
            poster.image = images[i];
            poster.title = titles[i];
            poster.rating = Float.parseFloat(ratings[i]);
            poster.producer = producers[i];
            poster.description = descriptions[i];
            posters.add(poster);
        }
        return posters;
    }

}
