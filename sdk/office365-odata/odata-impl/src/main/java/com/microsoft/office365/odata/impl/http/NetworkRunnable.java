/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information.
 ******************************************************************************/
package com.microsoft.office365.odata.impl.http;

import android.net.http.AndroidHttpClient;

import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.office365.odata.interfaces.Request;
import com.microsoft.office365.odata.interfaces.Response;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Runnable that executes a network operation
 */
public class NetworkRunnable implements Runnable {

	HttpURLConnection mConnection = null;
	InputStream mResponseStream = null;
	Request mRequest;
	SettableFuture<Response> mFuture;

	Object mCloseLock = new Object();

	/**
	 * Initializes the network runnable
	 * 
	 * @param request
	 *            The request to execute
	 * @param future
	 *            Future for the operation
     */
	public NetworkRunnable(Request request, SettableFuture<Response> future) {
		mRequest = request;
		mFuture = future;
	}


    @Override
    public void run() {
        AndroidHttpClient client = null;
        try {
            client = AndroidHttpClient.newInstance("Office-365-SDK");

            BasicHttpEntityEnclosingRequest realRequest = new BasicHttpEntityEnclosingRequest(mRequest.getVerb().toString(), mRequest.getUrl());
            EntityEnclosingRequestWrapper wrapper = new EntityEnclosingRequestWrapper(realRequest);

            Map<String, String> headers = mRequest.getHeaders();
            for (String key : headers.keySet()) {
                wrapper.addHeader(key, headers.get(key));
            }

            if (mRequest.getContent() != null) {
                ByteArrayEntity entity = new ByteArrayEntity(mRequest.getContent());
                wrapper.setEntity(entity);
            }

            HttpResponse realResponse = client.execute(wrapper);

            int status = realResponse.getStatusLine().getStatusCode();

            Map<String, List<String>> responseHeaders = new HashMap<String, List<String>>();
            for (Header header : realResponse.getAllHeaders()) {
                List<String> headerValues = new ArrayList<String>();
                for (HeaderElement element : header.getElements()) {
                    headerValues.add(element.getValue());
                }
                responseHeaders.put(header.getName(), headerValues);
            }

            InputStream stream = realResponse.getEntity().getContent();

            if (stream != null) {
                Response response = new ResponseImpl(
                        stream,
                        status,
                        responseHeaders,
                        client);

                mFuture.set(response);
            } else {
                client.close();
                mFuture.set(new EmptyResponse(status, responseHeaders));
            }

        } catch (Throwable t) {
            if (client != null) {
                client.close();
            }

            mFuture.setException(t);
        }
    }
/*
	@Override
	public void run() {
		try {
			int responseCode = -1;
			synchronized (mCloseLock) {
				if (!mFuture.isCancelled()) {
					if (mRequest == null) {
						mFuture.setException(new IllegalArgumentException(
								"request"));
						return;
					}

					mConnection = createHttpURLConnection(mRequest);

					responseCode = mConnection.getResponseCode();

					if (responseCode >= 400) {
						mResponseStream = mConnection.getErrorStream();
                        mErrorWasFound = true;
					} else {
						mResponseStream = mConnection.getInputStream();
					}
				}
			}

			if (mResponseStream != null && !mFuture.isCancelled()) {
				mFuture.set(new ResponseImpl(mResponseStream, responseCode,
						mConnection.getHeaderFields()));
			} else if (mErrorWasFound) {
                mFuture.set(new EmptyResponse(responseCode, mConnection.getHeaderFields()));
            }
		} catch (Throwable e) {
			if (!mFuture.isCancelled()) {
				if (mConnection != null) {
					mConnection.disconnect();
				}

				mFuture.setException(e);
			}
		} finally {
			closeStreamAndConnection();
		}

	}

    */

	/**
	 * Closes the stream and connection, if possible
	 */
	void closeStreamAndConnection() {
		synchronized (mCloseLock) {
			if (mResponseStream != null) {
				try {
					mResponseStream.close();
				} catch (IOException e) {
				}
			}

			if (mConnection != null) {
				mConnection.disconnect();
			}
		}
	}

	/**
	 * Creates an HttpURLConnection
	 * 
	 * @param request
	 *            The request info
	 * @return An HttpURLConnection to execute the request
	 * @throws java.io.IOException
	 */
	static HttpURLConnection createHttpURLConnection(Request request)
			throws IOException {
		URL url = new URL(request.getUrl());

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(request.getVerb().toString());

		Map<String, String> headers = request.getHeaders();

		for (String key : headers.keySet()) {
			connection.setRequestProperty(key, headers.get(key));
		}

		if (request.getContent() != null) {
			connection.setDoOutput(true);
			byte[] requestContent = request.getContent();
			OutputStream stream = connection.getOutputStream();
			stream.write(requestContent, 0, requestContent.length);
			stream.close();
		}

		return connection;
	}

}
