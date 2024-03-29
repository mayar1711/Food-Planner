package com.example.foodplanner.ui.favorite.view;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.model.data.Meal;
import com.example.foodplanner.model.repositry.localrepo.MealRepoImp;
import com.example.foodplanner.model.repositry.localrepo.MealLocalDatasourceImp;
import com.example.foodplanner.ui.favorite.presenter.FavMeal;
import com.example.foodplanner.ui.favorite.presenter.FavMealImp;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavMealView ,OnClickListener{
    private RecyclerView recyclerView;
    private FavMeal favMeal;
    private FavoriteMealAdapter myAdapter;
    boolean isnull ;
    public FavoriteFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        myAdapter = new FavoriteMealAdapter(new ArrayList<>());
        favMeal = FavMealImp.getInstance(
                MealRepoImp.getInstance(
                        MealLocalDatasourceImp.getInstance(getContext())
                ),
                this
        );

        recyclerView = view.findViewById(R.id.recycler1_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.onDeleteClickListener = this::deleteFavProduct;
        myAdapter.setListener(this);
        favMeal.getProducts(this);


        return view;
    }

    @Override
    public void deleteFavProduct(Meal meal) {
        favMeal.deleteFavoriteProduct(meal);
    }

    @Override
    public void onGetAllFavoriteProducts(List<Meal> favoriteMeal) {
        Log.i("TAG", "onGetAllFavoriteProducts: "+favoriteMeal.get(0).getStrMeal());
        if(favoriteMeal.size()!=0)
        {
            isnull=true;
        }
        else isnull=false;
        myAdapter.changeData(favoriteMeal);
        myAdapter.notifyDataSetChanged();
        Log.i("TAG", "onGetAllFavoriteProducts: " + favoriteMeal.size());
    }

    @Override
    public void onGetAllFavoriteProductsError(String errorMessage) {
    //    Toast.makeText(requireActivity(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickMeal(Meal meal) {
        if (requireActivity() != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("meal", meal);
            Navigation.findNavController(requireView()).navigate(R.id.action_navigation_favorite_to_mealDetailFragment, bundle);
        } else {
            Log.e("FavoriteFragment", "Fragment is not attached to an activity");
        }
    }

}
