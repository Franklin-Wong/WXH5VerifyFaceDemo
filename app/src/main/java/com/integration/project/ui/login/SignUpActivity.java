package com.integration.project.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.integration.project.BaseActivity;
import com.integration.project.R;
import com.integration.project.entity.SignUpEntity;
import com.integration.project.presenter.SignUpPresenter;
import com.integration.project.utils.Constants;
import com.integration.project.utils.FileUtil;
import com.integration.project.utils.LogUtils;
import com.integration.project.utils.MatchUtils;
import com.integration.project.utils.SPUtil;
import com.integration.project.view.login.ISignUpInterface;
import com.integration.project.widgets.PhotoDialog;
import com.integration.project.widgets.TimeCounter;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
/**
 *
 */
public class SignUpActivity extends BaseActivity<ISignUpInterface, SignUpPresenter>
        implements View.OnClickListener, ISignUpInterface, PhotoDialog.OnPayDialogListener {
    private static final String TAG = "SignUpActivity";
    public static final int TAKE_PHOTO = 1;
    public static final int OPEN_ALBUM = 2;

    private EditText mEtPhoneNumber, mEtPwd, mEtPwd2, mEtMessageCode, mEtRealName, mEtIdNumber;
    private Button mBtSign;
    private ImageView mIvHead, mIvEmblem;
    private CheckBox mCheckBox;
    private TextView mUserContract, mGetCode;

    private boolean isAgreed = false;
    private String mPhoneNumber, mPwd, mPwd2, mMessageCode, mRealName,mIdNumber;
    private String mUrlHead, mUrlEmblem;

    private AlertDialog.Builder mBuilder;

    private TimeCounter mTimeCounter;
    private Uri imageUri;
    private Integer mCardType = -1;
    private File mPhotofile;

    @Override
    public SignUpPresenter createPresenter() {
        return mPresenter = new SignUpPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        initEvent();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constants.REQUEST_READ_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    Constants.REQUEST_READ_PHONE_STATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_READ_PHONE_STATE) {
            LogUtils.i(TAG, "onRequestPermissionsResult: REQUEST_READ_PHONE_STATE = "+ grantResults.length);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "???????????????????????????????????????????????????",Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constants.REQUEST_WRITE_EXTERNAL_STORAGE) {
            LogUtils.i(TAG, "onRequestPermissionsResult: REQUEST_WRITE_EXTERNAL_STORAGE = "+ grantResults.length);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "?????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == Constants.REQUEST_READ_EXTERNAL_STORAGE) {
            LogUtils.i(TAG, "onRequestPermissionsResult: REQUEST_READ_EXTERNAL_STORAGE = "+ grantResults.length);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "?????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == TAKE_PHOTO) {
            LogUtils.i(TAG, "onRequestPermissionsResult: REQUEST_READ_EXTERNAL_STORAGE = "+ grantResults.length);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "?????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        mGoBack = findViewById(R.id.tvReturn);
        mTitle = findViewById(R.id.tvTitleName);
        mTitle.setText(R.string.signup);

        mEtPhoneNumber = findViewById(R.id.etPhoneNumber);
        mEtMessageCode = findViewById(R.id.etMessageCode);
        mEtPwd = findViewById(R.id.etPwd);
        mEtPwd2 = findViewById(R.id.etPwd2);
        mEtRealName = findViewById(R.id.etName);
        mEtIdNumber = findViewById(R.id.etIDNumber);
        mGetCode = findViewById(R.id.tvGetCode);

        mUserContract = findViewById(R.id.tvUserContract);
        mCheckBox = findViewById(R.id.checkbox);

        mIvHead = findViewById(R.id.ivHead);
        mIvEmblem = findViewById(R.id.ivEmblem);
        mBtSign = findViewById(R.id.btSignUp);

    }

    private void initEvent() {

        mGetCode.setOnClickListener(this);
        mBtSign.setOnClickListener(this);
        mUserContract.setOnClickListener(this);
        mIvHead.setOnClickListener(this);
        mIvEmblem.setOnClickListener(this);
        mCheckBox.setOnClickListener(this);
        mGoBack.setOnClickListener(this);

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAgreed = isChecked;
                mCheckBox.setChecked(isChecked);

            }
        });

        mEtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                mPhoneNumber = mEtPhoneNumber.getText().toString().trim();
            }
        });
        mEtMessageCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                mMessageCode = mEtMessageCode.getText().toString().trim();
            }
        });
        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPwd = mEtPwd.getText().toString().trim();
            }
        });
        mEtPwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                mPwd2 = mEtPwd2.getText().toString().trim();
            }
        });
        mEtRealName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                mRealName = mEtRealName.getText().toString().trim();
            }
        });
        mEtIdNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                mIdNumber = mEtIdNumber.getText().toString().trim();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSignUp:
                if (TextUtils.isEmpty(mPhoneNumber)) {
                    showToast("????????????????????????");
                    return;
                }
                if (!MatchUtils.isMobileRight(mPhoneNumber)) {
                    showToast("?????????????????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(mMessageCode)) {
                    showToast("?????????????????????");
                    return;
                }
                if (!MatchUtils.isVerifycationCodeRight(mMessageCode)) {
                    showToast("??????????????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(mPwd)) {
                    showToast("???????????????????????????");
                    return;
                }
                else if (!MatchUtils.isPwdRight(mPwd)) {
                    showToast("????????????????????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(mPwd2)) {
                    showToast("?????????????????????????????????");
                    return;
                }
                else if (!MatchUtils.isPwdRight(mPwd2)) {
                    showToast("??????????????????????????????????????????");
                    return;
                }
                if (!mPwd.equals(mPwd2)) {
                    showToast("??????????????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(mRealName)) {
                    showToast("???????????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(mIdNumber)) {
                    showToast("??????????????????????????????");
                    return;
                } else if (!MatchUtils.isCertifyIDNumber(mIdNumber)) {
                    showToast("???????????????????????????????????????");
                    return;
                }

                if (!isAgreed) {
                    showToast("???????????????????????????????????????");
                }

                mPresenter.checkPhone();
                break;
            case R.id.tvGetCode:
                if (TextUtils.isEmpty(mPhoneNumber)) {
                    showToast("????????????????????????");
                    return;
                }
                if (!MatchUtils.isMobileRight(mPhoneNumber)) {
                    showToast("?????????????????????????????????");
                    return;
                }

                mPresenter.getMessageCode();

                break;
            case R.id.ivHead:
                //??????????????????
                if (TextUtils.isEmpty(mRealName)) {
                    showToast("???????????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(mIdNumber)) {
                    showToast("??????????????????????????????");
                    return;
                } else if (!MatchUtils.isCertifyIDNumber(mIdNumber)) {
                    showToast("???????????????????????????????????????");
                    return;
                }
                mCardType = 0;
                PhotoDialog.showPhotoDialog(this, this);
                break;
            case R.id.ivEmblem:
                if (TextUtils.isEmpty(mRealName)) {
                    showToast("???????????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(mIdNumber)) {
                    showToast("??????????????????????????????");
                    return;
                } else if (!MatchUtils.isCertifyIDNumber(mIdNumber)) {
                    showToast("???????????????????????????????????????");
                    return;
                }
                mCardType = 1;
                PhotoDialog.showPhotoDialog(this, this);

                break;
            case R.id.tvUserContract:
                startActivity(new Intent(this, UserAgreeActivity.class));
                break;

            case R.id.tvReturn:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * ????????????
     */
    private void setOpenAlbum(){
        if (ContextCompat.checkSelfPermission(SignUpActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SignUpActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent, OPEN_ALBUM);
        }
    }

    /**
     * ?????????????????????????????????
     */
    private void openGallery() {
        //????????????
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //????????????????????????(???????????????????????????????????????????????????)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, OPEN_ALBUM);
        } else {
            //????????????????????????

            Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
            intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intentToPickPic, OPEN_ALBUM);
        }
    }

    /**
     * ????????????
     */
    private void openCamera() {
        //????????????
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        if (outputImage.exists()){
            outputImage.delete();
        }
        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //????????????????????????
        if (Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(SignUpActivity.this,
                    "com.integration.gonghuibao.fileprovider", outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }
        //???????????????
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent,TAKE_PHOTO);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    String photoPath = FileUtil.getFilePathByUri(this, imageUri);
                    if (!TextUtils.isEmpty(photoPath)) {
                        mPhotofile = new File(photoPath);
                        if (mCardType == 0) {
                            mPresenter.uploadHeadPic();
                        } else if (mCardType == 1) {
                            mPresenter.uploadEmblemPic();
                        }
                    }

                    break;
                case OPEN_ALBUM:

                    Uri uri = data.getData();
                    String filePath = null;
                    if (uri != null) {
                        filePath = FileUtil.getFilePathByUri(this, uri);
                        if (!TextUtils.isEmpty(filePath)) {
                            mPhotofile = new File(filePath);
                        }
                        if (mCardType == 0) {
                            mPresenter.uploadHeadPic();
                        } else if (mCardType == 1) {
                            mPresenter.uploadEmblemPic();
                        }
                    }

//                    showPicByPath(filePath);

                    break;
                default:
                    break;
            }
        }
    }

    private void showPicByPath(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            RequestOptions requestOptions1 = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
            //?????????????????? ivImage???
            Glide.with(this).load(filePath).apply(requestOptions1).into(mIvEmblem);
        }
    }

    /**
     * ?????????????????????????????????
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mIvEmblem.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    @Override
    public String getPwd() {
        return mPwd;
    }

    @Override
    public String getPwd2() {
        return mPwd2;
    }

    @Override
    public String getCode() {
        return mMessageCode;
    }

    @Override
    public String getRealName() {
        return mRealName;
    }

    @Override
    public String getIdNumber() {
        return mIdNumber;
    }

    @Override
    public String getUrlAvatar() {
        return mUrlHead;
    }

    @Override
    public String getUrlMetal() {
        return mUrlEmblem;
    }

    @Override
    public File getPhoto() {
        return mPhotofile;
    }

    @Override
    public void saveUserData(SignUpEntity data) {
        showToast("????????????");
        SPUtil.saveToken(this, data.getToken());
        SPUtil.saveEmployeeId(this, data.getEmployeeId());
        SPUtil.saveName(this, data.getName());
        //????????????,?????????????????????????????????
        Intent intent = new Intent(this, FaceActivity.class);
        intent.putExtra("url", data.getAuthUrl());
        intent.putExtra("telephone", mPhoneNumber);
        intent.putExtra("type", 1);
        startActivity(intent);
        finish();
    }

    @Override
    public void showRequestFailedView(String message) {
        showToast(message);
    }

    @Override
    public void showRequestSuccessView(String message) {
        showToast(message);
        //????????????
        mPresenter.signUp();

    }

    @Override
    public void showGetCodeSuccessView(String message) {
        mTimeCounter = new TimeCounter(60000, 1000, mGetCode);
        mTimeCounter.start();
        showToast(message);
    }

    @Override
    public void showGetCodeFailedView(String message) {
        mGetCode.setClickable(true);
        showToast(message);
    }

    @Override
    public Integer getIdCardType() {
        return mCardType;
    }

    @Override
    public void uploadHeadPicSuccess(String picUrl) {

        mUrlHead = picUrl;
        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(this)
                .load(Uri.parse(picUrl))
                .placeholder(R.mipmap.ic_head)
                .centerCrop()
                .apply(requestOptions)
                .priority(Priority.HIGH)
        .into(mIvHead);

    }

    @Override
    public void uploadEmblemPicSuccess(String picUrl) {
        mUrlEmblem = picUrl;
        RequestOptions requestOptions = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this)
                .load(Uri.parse(picUrl))
                .placeholder(R.mipmap.ic_emblem)
                .centerCrop()
                .apply(requestOptions)
                .priority(Priority.HIGH)
                .into(mIvEmblem);
    }


    @Override
    public void onCameraClick(View view) {
        //????????????
        if (ContextCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SignUpActivity.this,
                    new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
        }else {
            openCamera();
        }
    }

    @Override
    public void onGalleryClick(View view) {
        //??????
        setOpenAlbum();
    }
}
