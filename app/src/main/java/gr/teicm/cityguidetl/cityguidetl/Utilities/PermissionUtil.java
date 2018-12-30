package gr.teicm.cityguidetl.cityguidetl.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PermissionUtil {

	Context context;
	Activity currentActivity;

	PermissionResultCallback permissionResultCallback;


	ArrayList<String> permissionList =new ArrayList<>();
	ArrayList<String> listPermissionsNeeded=new ArrayList<>();

	String dialogContent ="";
	int reqCode;

	public PermissionUtil(Context context)
	{
		this.context=context;
		this.currentActivity = (Activity) context;

		permissionResultCallback= (PermissionResultCallback) context;


	}

	public PermissionUtil(Context context, PermissionResultCallback callback)
	{
		this.context=context;
		this.currentActivity = (Activity) context;

		permissionResultCallback= callback;


	}


	/**
	 * Check the API Level & Permission
	 *
	 * @param permissions
	 * @param dialog_content
	 * @param request_code
	 */

	public void checkPermission(ArrayList<String> permissions, String dialog_content, int request_code)
	{
		this.permissionList =permissions;
		this.dialogContent =dialog_content;
		this.reqCode =request_code;

		if(Build.VERSION.SDK_INT >= 23)
		{
			if (checkAndRequestPermissions(permissions, request_code))
			{
				permissionResultCallback.PermissionGranted(request_code);
				Log.i("all permissions", "granted");
				Log.i("proceed", "to callback");
			}
		}
		else
		{
			permissionResultCallback.PermissionGranted(request_code);

			Log.i("all permissions", "granted");
			Log.i("proceed", "to callback");
		}

	}


	/**
	 * Check and request the Permissions
	 *
	 * @param permissions
	 * @param request_code
	 * @return
	 */

	private  boolean checkAndRequestPermissions(ArrayList<String> permissions,int request_code) {

		if(permissions.size()>0)
		{
			listPermissionsNeeded = new ArrayList<>();

			for(int i=0;i<permissions.size();i++)
			{
				int hasPermission = ContextCompat.checkSelfPermission(currentActivity,permissions.get(i));

				if (hasPermission != PackageManager.PERMISSION_GRANTED) {
					listPermissionsNeeded.add(permissions.get(i));
				}

			}

			if (!listPermissionsNeeded.isEmpty())
			{
				ActivityCompat.requestPermissions(currentActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),request_code);
				return false;
			}
		}

		return true;
	}

	/**
	 *
	 *
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
	 */
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
	{
		switch (requestCode)
		{
			case 1:
				if(grantResults.length>0)
				{
					Map<String, Integer> perms = new HashMap<>();

					for (int i = 0; i < permissions.length; i++)
					{
						perms.put(permissions[i], grantResults[i]);
					}

					final ArrayList<String> pending_permissions=new ArrayList<>();

					for (int i = 0; i < listPermissionsNeeded.size(); i++)
					{
						if (perms.get(listPermissionsNeeded.get(i)) != PackageManager.PERMISSION_GRANTED)
						{
							if(ActivityCompat.shouldShowRequestPermissionRationale(currentActivity,listPermissionsNeeded.get(i)))
								pending_permissions.add(listPermissionsNeeded.get(i));
							else
							{
								Log.i("Go to settings","and enable permissions");
								permissionResultCallback.NeverAskAgain(reqCode);
								Toast.makeText(currentActivity, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
								return;
							}
						}

					}

					if(pending_permissions.size()>0)
					{
						showMessageOKCancel(dialogContent,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {

										switch (which) {
											case DialogInterface.BUTTON_POSITIVE:
												checkPermission(permissionList, dialogContent, reqCode);
												break;
											case DialogInterface.BUTTON_NEGATIVE:
												Log.i("permisson","not fully given");
												if(permissionList.size()==pending_permissions.size())
													permissionResultCallback.PermissionDenied(reqCode);
												else
													permissionResultCallback.PartialPermissionGranted(reqCode,pending_permissions);
												break;
										}


									}
								});

					}
					else
					{
						Log.i("all","permissions granted");
						Log.i("proceed","to next step");
						permissionResultCallback.PermissionGranted(reqCode);

					}



				}
				break;
		}
	}


	/**
	 * Explain why the app needs permissions
	 *
	 * @param message
	 * @param okListener
	 */
	private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
		new AlertDialog.Builder(currentActivity)
				.setMessage(message)
				.setPositiveButton("Ok", okListener)
				.setNegativeButton("Cancel", okListener)
				.create()
				.show();
	}

	public interface PermissionResultCallback
	{
		void PermissionGranted(int request_code);
		void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions);
		void PermissionDenied(int request_code);
		void NeverAskAgain(int request_code);
	}
}


