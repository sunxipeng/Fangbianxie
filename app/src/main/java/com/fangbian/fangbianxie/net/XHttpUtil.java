package com.fangbian.fangbianxie.net;

import android.content.Context;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2016/10/24.
 */
public class XHttpUtil {


    public static final String TAG = "XHttpUtil";

    private static XHttpUtil XHttpUtilInstance = null;

    private HttpUtils mHttpUtil = null;

    private XHttpUtil() {

        mHttpUtil = new HttpUtils(HttpConfigs.TIME_OUT, HttpConfigs.USER_AFENT);
        //设置默认请求的缓存时间
        mHttpUtil.configDefaultHttpCacheExpiry(0);
    }

    public static XHttpUtil getInstance() {

        if (XHttpUtilInstance == null) {

            XHttpUtilInstance = new XHttpUtil();
        }
        return XHttpUtilInstance;
    }


    public interface HttpCallBack {

        public void onSuccess(ResponseInfo<String> responseInfo, int resultCode);

        public void onFail(String errorInfo, int resultCode);
    }


    public void dogetFromServer(final Context context, String url, final HttpCallBack callBack, final int resultCode) {
        Log.i("a", "url=" + url);

       /* if (CommonUtil.isNetworkAvailable(context) == 0) {


            get(context, url, callBack, resultCode);

        } else {

            get(context, url, callBack, resultCode);
        }
*/

        get(context,url,callBack,resultCode);

    }


    //进行网络请求
    private void get(final Context context, String url, final HttpCallBack callBack, final int resultCode) {


        mHttpUtil.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException e, String s) {

                callBack.onFail(s, resultCode);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                callBack.onSuccess(responseInfo, resultCode);
            }

            @Override
            public void onCancelled() {

                super.onCancelled();
            }
        });

    }

    public void doPostToServer(HttpRequest.HttpMethod method, String url, RequestParams params, final HttpCallBack callBack, final int resultCode) {

        this.mHttpUtil.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onFailure(HttpException e, String s) {
                callBack.onFail(s, resultCode);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                callBack.onSuccess(responseInfo, resultCode);
            }
        });
    }
}
