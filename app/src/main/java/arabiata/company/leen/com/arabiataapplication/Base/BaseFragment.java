/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package arabiata.company.leen.com.arabiataapplication.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by janisharali on 27/01/17.
 */

public abstract class BaseFragment<Presenter extends BasePresenter>  extends Fragment {

    protected Presenter mPresenter;

    @NonNull
    protected abstract Presenter createPresenter(@NonNull final Context context);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter(getContext());
        mPresenter.onCreate(savedInstanceState);
         }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onViewCreated(view,savedInstanceState);
        setUp(view);
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
mPresenter.onAttach(context);
    }

*/


    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.onDetach();
    }








    protected abstract void setUp(View view);

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

}