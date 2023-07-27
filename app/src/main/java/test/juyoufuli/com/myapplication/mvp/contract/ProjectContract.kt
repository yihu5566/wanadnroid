package test.juyoufuli.com.myapplication.mvp.contract

import androidx.fragment.app.Fragment
import com.jess.arms.mvp.IModel
import com.jess.arms.mvp.IView
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.entity.ProjectDetailsResponse
import test.juyoufuli.com.myapplication.mvp.entity.ProjectResponse

/**
 * Author : dongfang
 *
 * Created Time : 2018-10-31  09:36
 * Description:
 */
class ProjectContract {
    interface View : IView {
        val fragment: Fragment
        fun refreshAdapterList(response: ProjectResponse)
        fun refreshDetailsAdapterList(response: ProjectDetailsResponse)

    }

    interface Model : IModel {
        fun getProjectData(): Observable<ProjectResponse>
        fun getProjectDetailsData(page: String, cid: String): Observable<ProjectDetailsResponse>
        fun collectArticle(id: String): Observable<LoginResponse>
        fun cancelCollectArticle(id: String): Observable<LoginResponse>
    }
}