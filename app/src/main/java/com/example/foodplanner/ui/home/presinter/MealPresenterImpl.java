package com.example.foodplanner.ui.home.presinter;

import com.example.foodplanner.model.repositry.remoterepo.RepositoryInterface;
import com.example.foodplanner.model.response.MealResponse;
import com.example.foodplanner.ui.home.view.MealView;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

public class MealPresenterImpl implements MealPresenter {
    private final RepositoryInterface repository;
    private final MealView view;
    public MealPresenterImpl(RepositoryInterface repository, MealView view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public Disposable getMealList() {
        return repository.getMealList(new io.reactivex.rxjava3.core.SingleObserver<MealResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull MealResponse mealResponse) {
                  if(mealResponse!=null)
                  {
                      view.displayMeals(mealResponse.getMeals());
                  }
                  else
                      view.displayError("Failed to fetch Meal");
            }
            @Override
            public void onError(@NonNull Throwable e) {
                 view.displayError(e.getMessage());
            }
        });
    }
}

