package cn.edu.jxnu.awesome_campus.ui.library;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.SelfStudySeatLeftModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.login.SelfStudyRoomLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;
import cn.edu.jxnu.awesome_campus.ui.login.StudyLoginFragment;

/**
 * Created by MummyDing on 16-4-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class SelfStudySeatFragment extends BaseListFragment{

    private SelfStudySeatLeftModel model;

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.seats);
    }

    @Override
    public void bindAdapter() {
        model = new SelfStudySeatLeftModel();
    }

    @Override
    public void addHeader() {

    }

    @Override
    public void initView() {


        // 判断是否登陆
        if (SelfStudyRoomLoginUtil.isLogin()){
            model.loadFromCache();
            setOnLineLayout(true);
            Toast.makeText(getActivity(),"登陆了",Toast.LENGTH_SHORT).show();
        }else {
            setOnLineLayout(false);
            Toast.makeText(getActivity(),"没有",Toast.LENGTH_SHORT).show();
        }

    }

    private static Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);

        switch (eventModel.getEventCode()){
            case EVENT.SELF_STUDY_SEATS_LOAD_CACHE_SUCCESS:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onDataRefresh();
                    }
                },1500);
            case EVENT.SELF_STUDY_SEATS_REFRESH_SUCCESS:
                adapter.newList(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.SELF_STUDY_SEATS_REFRESH_FAILURE:
                hideLoading();
                displayLoading();
                break;
            case EVENT.SELF_STUDY_SEATS_LOAD_CACHE_FAILURE:
                onDataRefresh();
                break;
        }
    }
}
