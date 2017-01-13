package com.graduation.chenyou.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.VolleyError;
import com.buu.bysj.domain.JsonToBeanUtils;
import com.buu.bysj.domain.Msg;
import com.buu.bysj.domain.Student;
import com.buu.bysj.domain.Works;
import com.graduation.chenyou.R;
import com.graduation.chenyou.adapter.StudentParentAdapter;
import com.graduation.chenyou.adapter.WorksAdapter;
import com.graduation.chenyou.util.FileImageUpload;
import com.graduation.chenyou.util.ModifyAvatarDialog;
import com.graduation.chenyou.util.UserInfo;
import com.volley.VolleyInterface;
import com.volley.VolleyRequest;

public class ActivityAddStudentWorks extends Activity implements OnClickListener {
	private ImageView iv_add_new_works_back_arrow;
	private EditText et_new_works_name;
	private ImageView iv_et_new_works_picurl;
	private Button btn_save_new_works;
	//已有作品
	private ListView lv_student_works_list;
	
	//上下文
	private Context mContext;
	//学生id
	private String studentId;
	// 上传图片定义路径
	private static final int FLAG_CHOOSE_IMG = 5;
	private static final int FLAG_CHOOSE_PHONE = 6;
	private static final int FLAG_MODIFY_FINISH = 7;
	private static String localTempImageFileName = "";
	public static final String IMAGE_PATH = "my_headImage";
	public static final File FILE_SDCARD = Environment
			.getExternalStorageDirectory();
	public static final File FILE_LOCAL = new File(FILE_SDCARD, IMAGE_PATH);
	public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL,
			"images/screenshots");
	public static String picurl = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_student_works);
		//获取上下文
		mContext = getApplicationContext();
		//获取学生ID
		Intent intent = getIntent();
		studentId = intent.getStringExtra("studentId");
		getView2Init();
		//填充数据
		setStudentWorksListData(mContext);
		
	}

	// /**
	// * 获得布局文件上的控件并初始化
	// */
	private void getView2Init() {
	
		iv_add_new_works_back_arrow = (ImageView) findViewById(R.id.iv_add_new_works_back_arrow);
		et_new_works_name = (EditText) findViewById(R.id.et_new_works_name);
		iv_et_new_works_picurl = (ImageView) findViewById(R.id.iv_et_new_works_picurl);
		btn_save_new_works = (Button) findViewById(R.id.btn_save_new_works);
		//已有作品
		lv_student_works_list = (ListView) findViewById(R.id.lv_student_works_list);

		
		// 设置点击事件
		iv_add_new_works_back_arrow.setOnClickListener(this);
		iv_et_new_works_picurl.setOnClickListener(this);
		btn_save_new_works.setOnClickListener(this);
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		// 返回
		if (v == iv_add_new_works_back_arrow) {
			finish();
		} else if (v == btn_save_new_works) {
			//保存
			saveNewWorks(mContext);
		}else if (v == iv_et_new_works_picurl) {
			//上传头像
			addPicture();
		}
	}

	//保存
	private void saveNewWorks(final Context context) {
		Toast.makeText(context, picurl,
				Toast.LENGTH_LONG).show();
		final String name = et_new_works_name.getText().toString().trim();
		if ("".equals(name)) {
			Toast.makeText(context, "作品名称不能为空",
					Toast.LENGTH_LONG).show();
			return;
		}
		String saveNewWorksUrl = JsonToBeanUtils.url + "addWorks";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("sid", studentId);
		parameter.put("tid", UserInfo.uid);
		parameter.put("name", name);
		parameter.put("picurl", picurl);
		VolleyRequest.RequestPost(saveNewWorksUrl, "addWorks", parameter,
				new VolleyInterface() {
					// 网络失败
					@Override
					public void onfailure(VolleyError error) {
						// error.toString()
						Toast.makeText(getApplicationContext(), "网络异常！",
								Toast.LENGTH_LONG).show();
					}

					// 网络成功
					@Override
					public void onSuccess(String result) {
						try {
							JSONObject obj = new JSONObject(result);
							Msg msg = JsonToBeanUtils.JsonToMsg(obj);
							// 保存失败
							if (msg.getStatus().equals("error")) {
								Toast.makeText(context,msg.getMsg(), Toast.LENGTH_LONG).show();
								// 保存成功
							} else if (msg.getStatus().equals("success")) {
								Toast.makeText(context, "保存成功",Toast.LENGTH_LONG).show();
								et_new_works_name.setText(null);
								//刷新列表
								setStudentWorksListData(context);
							} else {
								Toast.makeText(getApplicationContext(),
										"服务器繁忙，请稍后", Toast.LENGTH_LONG).show();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}
	
	// 填充作品列表数据
		private void setStudentWorksListData(final Context context) {
			// 获取关注的用户
			String getStudentWorksUrl = JsonToBeanUtils.url + "getStudentWorks";
			Map<String, String> parameter = new HashMap<String, String>();
//			parameter.put("tid", UserInfo.uid);
			parameter.put("sid", studentId);
			// 网络请求
			VolleyRequest.RequestPost(getStudentWorksUrl, "getStudentWorks",
					parameter, new VolleyInterface() {
						// 请求失败
						@Override
						public void onfailure(VolleyError error) {
							// error.toString()
							Toast.makeText(context, "网络异常", Toast.LENGTH_LONG)
									.show();
						}
						// 请求成功
						@Override
						public void onSuccess(String result) {
							try {
								JSONArray arr = new JSONArray(result);
								final ArrayList<Works> studentWorksList = (ArrayList<Works>) JsonToBeanUtils.JsonToWorksList(arr);

								// 设置数据设配器
								lv_student_works_list.setAdapter(new WorksAdapter(
										context, studentWorksList));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});

		}
	
	
	
	
	
	
	
	
	
	
	 //上传图片
		private void addPicture() {
			// 调用选择那种方式的dialog
			ModifyAvatarDialog modifyAvatarDialog = new ModifyAvatarDialog(this,
					R.style.dialog_select) {
				// 选择本地相册
				@Override
				public void doGoToImg() {
					this.dismiss();
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_PICK);
					intent.setType("image/*");
					startActivityForResult(intent, FLAG_CHOOSE_IMG);
				}

				// 选择相机拍照
				@Override
				public void doGoToPhone() {
					this.dismiss();
					String status = Environment.getExternalStorageState();
					if (status.equals(Environment.MEDIA_MOUNTED)) {
						try {
							localTempImageFileName = "";
							localTempImageFileName = String.valueOf((new Date())
									.getTime()) + ".png";
							File filePath = FILE_PIC_SCREENSHOT;
							if (!filePath.exists()) {
								filePath.mkdirs();
							}
							Intent intent = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
							File f = new File(filePath, localTempImageFileName);
							// localTempImgDir和localTempImageFileName是自己定义的名字
							Uri u = Uri.fromFile(f);
							intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
							intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
							startActivityForResult(intent, FLAG_CHOOSE_PHONE);
						} catch (ActivityNotFoundException e) {
						}
					}
				}
			};

			AlignmentSpan span = new AlignmentSpan.Standard(
					Layout.Alignment.ALIGN_CENTER);
			AbsoluteSizeSpan span_size = new AbsoluteSizeSpan(25, true);
			SpannableStringBuilder spannable = new SpannableStringBuilder();
			String dTitle = "上传图片";
			spannable.append(dTitle);
			spannable.setSpan(span, 0, dTitle.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			spannable.setSpan(span_size, 0, dTitle.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			modifyAvatarDialog.setTitle(spannable);
			modifyAvatarDialog.show();
		}

		// 点击拿到图片
		// 2.点击头像按钮，选择完照片返回的方法
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {
				if (data != null) {
					Uri uri = data.getData();
					if (!TextUtils.isEmpty(uri.getAuthority())) {
						Cursor cursor = getContentResolver().query(uri,
								new String[] { MediaStore.Images.Media.DATA },
								null, null, null);
						if (null == cursor) {
							Toast.makeText(getApplicationContext(), "图片没找到", 0)
									.show();
							return;
						}
						cursor.moveToFirst();
						String path = cursor.getString(cursor
								.getColumnIndex(MediaStore.Images.Media.DATA));
						cursor.close();
						getPicPath(path);
					} else {
						getPicPath(uri.getPath());
					}
				}
			} else if (requestCode == FLAG_CHOOSE_PHONE && resultCode == RESULT_OK) {
				File f = new File(FILE_PIC_SCREENSHOT, localTempImageFileName);
				getPicPath(f.getAbsolutePath());
			} else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {
				if (data != null) {
					final String path = data.getStringExtra("path");
					SharedPreferences sharedPreferences = getSharedPreferences(
							"head_image_path", Context.MODE_PRIVATE); // 私有数据
					Editor editor = sharedPreferences.edit();// 获取编辑器
					editor.putString("path", path);
					editor.commit();// 提交修改
					getPicPath(path);
				}
			}
		}

		// 3拿到图片路径+回显
		public void getPicPath(String url) {
			// Toast.makeText(getApplicationContext(), url, 1).show();
			// 回显
			Bitmap bitmap = BitmapFactory.decodeFile(url);
			iv_et_new_works_picurl.setImageBitmap(bitmap);

			// 上传图片
			final String RequestURL = JsonToBeanUtils.url+"fileupload";
			final File file = new File(url);
			//
			Thread thread = new Thread(new Runnable() {
				public void run() {
					String result = FileImageUpload.uploadFile(file, RequestURL);
					try {
						JSONObject obj = new JSONObject(result);
						Msg msg = JsonToBeanUtils.JsonToMsg(obj);
						picurl = msg.getMsg();
					} catch (Exception e) {

					}

				}
			});
			thread.start();
		}

}
