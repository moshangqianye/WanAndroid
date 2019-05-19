package per.goweii.wanandroid.module.navigation.fragment;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import per.goweii.basic.core.base.BaseFragment;
import per.goweii.basic.utils.ToastMaker;
import per.goweii.wanandroid.R;
import per.goweii.wanandroid.module.main.activity.WebActivity;
import per.goweii.wanandroid.module.main.model.ArticleBean;
import per.goweii.wanandroid.module.navigation.adapter.NaviAdapter;
import per.goweii.wanandroid.module.navigation.model.NaviBean;
import per.goweii.wanandroid.module.navigation.presenter.NaviPresenter;
import per.goweii.wanandroid.module.navigation.view.NaviView;

/**
 * @author CuiZhen
 * @date 2019/5/12
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class NaviFragment extends BaseFragment<NaviPresenter> implements NaviView {

    @BindView(R.id.rv)
    RecyclerView rv;

    private NaviAdapter mAdapter;

    public static NaviFragment create() {
        return new NaviFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_navigation_child;
    }

    @Nullable
    @Override
    protected NaviPresenter initPresenter() {
        return new NaviPresenter();
    }

    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NaviAdapter();
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnItemClickListener(new NaviAdapter.OnItemClickListener() {
            @Override
            public void onClick(ArticleBean bean, int pos) {
                WebActivity.start(getContext(), bean.getTitle(), bean.getLink());
            }
        });
        rv.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void onVisibleFirst() {
        super.onVisibleFirst();
        presenter.getKnowledgeList();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if (mAdapter.getData().isEmpty()) {
            presenter.getKnowledgeList();
        }
    }

    @Override
    public void getNaviListSuccess(int code, List<NaviBean> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void getNaviListFail(int code, String msg) {
        ToastMaker.showShort(msg);
    }
}