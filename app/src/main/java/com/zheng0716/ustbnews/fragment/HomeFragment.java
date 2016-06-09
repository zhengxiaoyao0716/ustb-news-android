package com.zheng0716.ustbnews.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.zheng0716.ustbnews.R;
import com.zheng0716.ustbnews.View.RecordList;
import com.zheng0716.ustbnews.View.SnackBar;
import com.zheng0716.ustbnews.data.DataUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.homeView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onButtonPressed();
                return false;
            }
        });
        initList(view, inflater, container);
        view.findViewById(R.id.moreNews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SnackBar(mListener.getActivity(), "Sorry, this just a demo.", "ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/4/10 查看更多新闻
                        FragmentManager.replace(mListener.getActivity(), R.id.fragment, new NewFragment());
                    }
                }).show();
            }
        });
        view.findViewById(R.id.moreNotice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SnackBar(mListener.getActivity(), "Sorry, this just a demo.", "ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/4/10 查看更多新闻
                        FragmentManager.replace(mListener.getActivity(), R.id.fragment, new NewFragment());
                    }
                }).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private SwipeRefreshLayout refreshLayout;
    private void initList(final View parent, final LayoutInflater inflater, final ViewGroup container) {
        refreshLayout = (SwipeRefreshLayout) parent.findViewById(R.id.refresh);
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary_light);
        refreshLayout.setColorSchemeColors(Color.WHITE);
        final SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataUtil.INSTANCE.getData(mListener.getActivity(), "home", new DataUtil.DataHandler() {
                    @Override
                    public void handleData(JSONObject data) {
                        refreshLayout.setRefreshing(false);
                        if (data == null) {
                            try {
                                data = new JSONObject("{\n" +
                                        "      \"news\": [\n" +
                                        "        {\n" +
                                        "          \"date\": \"error\", \n" +
                                        "          \"href\": \"null\", \n" +
                                        "          \"text\": \"" + getContext().getString(R.string.noneConnect) + "\"\n" +
                                        "        }\n" +
                                        "      ], \n" +
                                        "      \"notice\": [\n" +
                                        "        {\n" +
                                        "          \"date\": \"error\", \n" +
                                        "          \"href\": \"null\", \n" +
                                        "          \"text\": \"" + getContext().getString(R.string.noneConnect) + "\"\n" +
                                        "        }\n" +
                                        "      ]\n" +
                                        "    }");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        new RecordList(parent, R.id.newsList, data.optJSONArray("news"), inflater, container, mListener.getActivity());
                        new RecordList(parent, R.id.noticeList, data.optJSONArray("notice"), inflater, container, mListener.getActivity());
                    }
                });
            }
        };
        listener.onRefresh();
        refreshLayout.setOnRefreshListener(listener);
    }

    private FragmentManager.OnFragmentInteractionListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentManager.OnFragmentInteractionListener) {
            mListener = (FragmentManager.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    // DO: Rename method, update argument and hook method into UI event
    private void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
