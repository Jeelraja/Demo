package com.builderfly.parsing_app.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static okhttp3.MultipartBody.FORM;


public class OkHttpRequest {

   private static final String TAG = OkHttpRequest.class.getSimpleName();

   private Map<String, String> mBody = new HashMap<String, String>();
   private Map<String, String> mHeaders = new HashMap<String, String>();
   private Map<String, File> mFile = new HashMap<String, File>();

   private String mUrl = "";
   private int mRequestId;
   private JSONObject jsonObject;
   private JSONArray jsonArray;
   private boolean mIsShowProgressDialog;

   private Activity mActivity;
   private Method mRequestType;
   private RequestBody mRequestBody;
   private OkHttpInterface mObjInterface;
   private ProgressDialog mProgressDialog;


   public OkHttpRequest() {

   }

   // Get all Json object and initiate API call
   public OkHttpRequest(Activity activity, Method requestType, String url, JSONObject jsonObject,
                        Map headers, int type, boolean isShowDialog, OkHttpInterface objInterface, Map mHeaders) {
      this.mActivity = activity;
      this.mRequestType = requestType;
      this.jsonObject = jsonObject;
      this.mHeaders = mHeaders;
      this.mFile = null;
      this.mIsShowProgressDialog = isShowDialog;
      this.mRequestId = type;
      this.mObjInterface = objInterface;
      this.mRequestBody = null;
      this.mUrl = url;

      // Call API Request
      callWebApiRequestForJson();
   }

   // Get all Json array and initiate API call
   public OkHttpRequest(Activity activity, Method requestType, String url, JSONArray jsonArray,
                        Map headers, int type, boolean isShowDialog, OkHttpInterface objInterface, Map mHeaders) {
      this.mActivity = activity;
      this.mRequestType = requestType;
      this.jsonArray = jsonArray;
      this.mHeaders = mHeaders;
      this.mFile = null;
      this.mIsShowProgressDialog = isShowDialog;
      this.mRequestId = type;
      this.mObjInterface = objInterface;
      this.mRequestBody = null;
      this.mUrl = url;

      // Call API Request
      callWebApiRequestForJsonArray();
   }


   // Get all requested parameter and initiate API call without File
   public OkHttpRequest(Activity activity, Method requestType, String url, Map<String, String> params,
                        Map<String, String> headers, int type, boolean isShowDialog, OkHttpInterface objInterface) {
      this.mActivity = activity;
      this.mRequestType = requestType;
      this.mBody = params;
      this.mHeaders = headers;
      this.mFile = null;
      this.mIsShowProgressDialog = isShowDialog;
      this.mRequestId = type;
      this.mObjInterface = objInterface;
      this.mRequestBody = null;
      this.mUrl = url;

      // Call API Request
      callWebApiRequest();
   }

   // Get all requested parameter and initiate API call with File
   public OkHttpRequest(Activity activity, Method requestType, String url, Map<String, String> params,
                        Map<String, String> headers, Map<String, File> files, int type, boolean isShowDialog,
                        OkHttpInterface objInterface) {
      this.mActivity = activity;
      this.mRequestType = requestType;
      this.mBody = params;
      this.mHeaders = headers;
      this.mFile = files;
      this.mIsShowProgressDialog = isShowDialog;
      this.mRequestId = type;
      this.mObjInterface = objInterface;
      this.mRequestBody = null;
      this.mUrl = url;

      // Call API Request
      callWebApiRequest();
   }

   private synchronized void callWebApiRequestForJson() {
      // Request Method
      switch (mRequestType) {
         case POST:

            if (jsonObject != null) {
               new networkAsyncCall().execute();

            } else {
               Log.e(TAG, "Add minimum one parameter");
            }
            break;

         default:
            break;
      }
   }

   private synchronized void callWebApiRequestForJsonArray() {
      // Request Method
      switch (mRequestType) {
         case POST:

            if (jsonArray != null) {
               new networkAsyncCallArray().execute();

            } else {
               Log.e(TAG, "Add minimum one parameter");

            }
            break;

         default:
            break;
      }
   }

   public static void cancelOkHttpRequest(String url) {
      try {
         HTTPUtils.cancelRequest(url);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private synchronized void callWebApiRequest() {
      // Request Method
      switch (mRequestType) {
         case GET:
            Log.e(TAG, "URL==>" + mUrl);
            mUrl = generateGetUrl();
            new networkAsyncCall().execute();
            break;
         case POST:
            Log.e(TAG, "URL==>" + mUrl);

            if (mFile != null) {
               mRequestBody = generatePostUrl();

               new networkAsyncCall().execute();

            } else {
               jsonObject = generatePostUrlEncoded();

               if (jsonObject != null) {
                  new networkAsyncCall().execute();
               } else {
                  Log.e(TAG, "Add minimum one parameter");
               }
            }
            break;
         case DELETE:
            Log.e(TAG, "URL==>" + mUrl);
            break;
         default:
            break;
      }
   }

   // Show progress
   private void showProgress() {
      if (mIsShowProgressDialog) {
         mProgressDialog = getProgressDialog(mActivity);
      }
   }

   // Dismiss progress
   private void dismissProgress() {
      if (mIsShowProgressDialog) {
         if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
         }
      }
   }

   private ProgressDialog getProgressDialog(Context c) {
      ProgressDialog pDialog = new ProgressDialog(c);
      pDialog.setMessage("Please wait..");
      pDialog.setCancelable(false);
      pDialog.show();
      return pDialog;
   }

   // Create Get url using HashMap
   private String generateGetUrl() {
      boolean isFirstParam = true;
      for (String key : mBody.keySet()) {
         if (isFirstParam) {
            mUrl = mUrl + "?" + key + "=" + mBody.get(key);
            isFirstParam = false;
         } else {
            mUrl = mUrl + "&" + key + "=" + mBody.get(key);
         }
      }
      return mUrl;
   }

   // Create RequestBody using HashMap
   private RequestBody generatePostUrl() {
      if (mBody != null && mBody.size() < 1) {
         return null;
      }
      MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(FORM);
      for (String key : mBody.keySet()) {
         multipartBuilder.addFormDataPart("" + key, "" + mBody.get(key));
      }

      if (mFile != null) {
         for (String key : mFile.keySet()) {
            String strValue = mFile.get("" + key).toString();
            if (strValue != null && key.contains("multiple_image")) {
               multipartBuilder.addFormDataPart("image[]", "" + mFile.get("" + key).getName(), RequestBody.create(MediaType.parse("image"), mFile.get("" + key)));

            } else if (strValue != null) {
               //multipartBuilder.addFormDataPart(""+key, "file_"+ mFile.get("" + key).getName(), RequestBody.create(MediaType.parse("image"), mFile.get("" + key)));
               multipartBuilder.addFormDataPart("" + key, "file_" + mFile.get("" + key).getName(), RequestBody.create(MediaType.parse("image"), new File(mFile.get("" + key).toString())));

            }
         }
      }

      if (mFile != null) {
         for (String key : mFile.keySet()) {
            if (mFile.get("" + key) != null) {
               multipartBuilder.addFormDataPart("" + key, "file_" + mFile.get("" + key).getName(), RequestBody.create(MediaType.parse("image"), new File(mFile.get("" + key).toString())));
            }
         }
      }
      RequestBody formBody = multipartBuilder.build();
      return formBody;
   }

   // Create RequestBody using HashMap
   private JSONObject generatePostUrlEncoded() {
      if (mBody != null && mBody.size() < 1) {
         return null;
      }

      MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(FORM);
      /* Without image file */
        /*for (String key : mBody.keySet()) {
            multipartBuilder.addFormDataPart("" + key, "" + mBody.get(key));
        }*/

      JSONObject jsonObject = new JSONObject();
      try {
         if (mFile != null) {
            for (String key : mFile.keySet()) {
               if (mFile.get("" + key) != null) {
                  multipartBuilder.addFormDataPart("" + key, "file_" + mFile.get("" + key).getName(), RequestBody.create(MediaType.parse("image"), new File(mFile.get("" + key).toString())));
               }

               RequestBody formBody = multipartBuilder.build();
               return jsonObject;
            }
         }
         for (String key : mBody.keySet()) {
            jsonObject.put("" + key, "" + mBody.get(key));
         }
      } catch (JSONException e) {
         e.printStackTrace();
      }
      return jsonObject;
   }

   // Call request in background thread
   public class networkAsyncCall extends AsyncTask<Void, Void, String> {


      @Override
      protected void onPreExecute() {
         showProgress();
         mObjInterface.onOkHttpStart(mRequestId);
      }

      @Override
      protected String doInBackground(Void... voids) {
         String response = "" + null;
         try {
            if (mRequestType == Method.POST) {

               if (mHeaders != null) {
                  if (mFile != null) {
                     response = HTTPUtils.postRunWithImage(mUrl, mRequestBody, mHeaders);
                  } else {
                     response = HTTPUtils.postRunWithHeader(mUrl, jsonObject.toString(), mHeaders);
                  }
               } else {
                  if (mFile != null) {
                     response = HTTPUtils.postRunWithImage(mUrl, mRequestBody, mHeaders);
                  } else {
                     response = HTTPUtils.postRun(mUrl, jsonObject.toString());
                  }

               }
            }
            if (mRequestType == Method.GET) {
               response = HTTPUtils.getRun(mUrl, mHeaders);
            }

         } catch (final IOException e) {
            final String finalResponse = response;
            if (mActivity != null) {
               mActivity.runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     mObjInterface.onOkHttpFailure(mRequestId, 0, finalResponse, e);
                  }
               });
            }
            e.printStackTrace();
         }
         return response;
      }

      @Override
      protected void onPostExecute(String response) {
         if (response != null) {
            mObjInterface.onOkHttpSuccess(mRequestId, 1, response);
         }
         dismissProgress();
         mObjInterface.onOkHttpFinish(mRequestId);
      }
   }

   // Call request in background thread
   public class networkAsyncCallArray extends AsyncTask<Void, Void, String> {

      @Override
      protected void onPreExecute() {
         // if (!mUrl.contains("companies")) {
         showProgress();
         //  }
         mObjInterface.onOkHttpStart(mRequestId);
      }

      @Override
      protected String doInBackground(Void... voids) {
         String response = "" + null;
         try {
            if (mRequestType == Method.POST) {

               if (mHeaders != null) {
                  if (mFile != null) {
                     response = HTTPUtils.postRunWithImage(mUrl, mRequestBody, mHeaders);
                  } else {
                     response = HTTPUtils.postRunWithHeader(mUrl, jsonArray.toString(), mHeaders);
                  }
               } else {
                  if (mFile != null) {
                     response = HTTPUtils.postRunWithImage(mUrl, mRequestBody, mHeaders);
                  } else {
                     response = HTTPUtils.postRun(mUrl, jsonArray.toString());
                  }

               }
            }
            if (mRequestType == Method.GET) {
               response = HTTPUtils.getRun(mUrl, mHeaders);
            }

         } catch (final IOException e) {
            final String finalResponse = response;
            if (mActivity != null) {
               mActivity.runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     mObjInterface.onOkHttpFailure(mRequestId, 0, finalResponse, e);
                  }
               });
            }
            e.printStackTrace();
         }
         return response;
      }

      @Override
      protected void onPostExecute(String response) {
         if (response != null) {
            mObjInterface.onOkHttpSuccess(mRequestId, 1, response);
         }
         dismissProgress();

         mObjInterface.onOkHttpFinish(mRequestId);
      }
   }

   // Request method
   public enum Method {
      POST, GET, DELETE
   }
}