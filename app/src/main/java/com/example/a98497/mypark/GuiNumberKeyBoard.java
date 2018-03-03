package com.example.a98497.mypark;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class GuiNumberKeyBoard extends RelativeLayout implements
		OnClickListener {

	private View view;
	private Button numberKeyboard_0;
	private Button numberKeyboard_1;
	private Button numberKeyboard_2;
	private Button numberKeyboard_3;
	private Button numberKeyboard_4;
	private Button numberKeyboard_5;
	private Button numberKeyboard_6;
	private Button numberKeyboard_7;
	private Button numberKeyboard_8;
	private Button numberKeyboard_9;
	private Button numberKeyboard_hint;

	private Animation mHiddenAction;
	private Animation mShowAction;

	private EditText editText;

	public GuiNumberKeyBoard(Context context, EditText editText) {
		super(context);
		this.editText = editText;
		init(context);

	}

	public GuiNumberKeyBoard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public GuiNumberKeyBoard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		view = LayoutInflater.from(context).inflate(R.layout.number_keyboard,
				this, true);
		numberKeyboard_0 = (Button) view.findViewById(R.id.numberKeyboard_0);
		numberKeyboard_1 = (Button) view.findViewById(R.id.numberKeyboard_1);
		numberKeyboard_2 = (Button) view.findViewById(R.id.numberKeyboard_2);
		numberKeyboard_3 = (Button) view.findViewById(R.id.numberKeyboard_3);
		numberKeyboard_4 = (Button) view.findViewById(R.id.numberKeyboard_4);
		numberKeyboard_5 = (Button) view.findViewById(R.id.numberKeyboard_5);
		numberKeyboard_6 = (Button) view.findViewById(R.id.numberKeyboard_6);
		numberKeyboard_7 = (Button) view.findViewById(R.id.numberKeyboard_7);
		numberKeyboard_8 = (Button) view.findViewById(R.id.numberKeyboard_8);
		numberKeyboard_9 = (Button) view.findViewById(R.id.numberKeyboard_9);
		numberKeyboard_hint = (Button) view
				.findViewById(R.id.numberKeyboard_hint);

		numberKeyboard_0.setOnClickListener(this);
		numberKeyboard_1.setOnClickListener(this);
		numberKeyboard_2.setOnClickListener(this);
		numberKeyboard_3.setOnClickListener(this);
		numberKeyboard_4.setOnClickListener(this);
		numberKeyboard_5.setOnClickListener(this);
		numberKeyboard_6.setOnClickListener(this);
		numberKeyboard_7.setOnClickListener(this);
		numberKeyboard_8.setOnClickListener(this);
		numberKeyboard_9.setOnClickListener(this);
		numberKeyboard_hint.setOnClickListener(this);

		mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAction.setDuration(500);
		mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f);
		mHiddenAction.setDuration(5000);
	}

	public void setEditText(EditText editText) {
		this.editText = editText;
		this.editText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				show();
				return false;
			}
		});
		this.editText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					hint();
				}
			}
		});
	}

	public void hint() {
		if (this.getVisibility() == View.VISIBLE) {
			this.startAnimation(mHiddenAction);
			this.setVisibility(View.GONE);
		}
	}

	public void show() {
		if (this.getVisibility() == View.GONE) {
			this.startAnimation(mShowAction);
			this.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.numberKeyboard_0:
			editText.setText(editText.getText().toString() + "0");
			break;
		case R.id.numberKeyboard_1:
			editText.setText(editText.getText().toString() + "1");
			break;
		case R.id.numberKeyboard_2:
			editText.setText(editText.getText().toString() + "2");
			break;
		case R.id.numberKeyboard_3:
			editText.setText(editText.getText().toString() + "3");
			break;
		case R.id.numberKeyboard_4:
			editText.setText(editText.getText().toString() + "4");
			break;
		case R.id.numberKeyboard_5:
			editText.setText(editText.getText().toString() + "5");
			break;
		case R.id.numberKeyboard_6:
			editText.setText(editText.getText().toString() + "6");
			break;
		case R.id.numberKeyboard_7:
			editText.setText(editText.getText().toString() + "7");
			break;
		case R.id.numberKeyboard_8:
			editText.setText(editText.getText().toString() + "8");
			break;
		case R.id.numberKeyboard_9:
			editText.setText(editText.getText().toString() + "9");
			break;
		case R.id.numberKeyboard_hint:
			hint();
			break;
		}
	}
}
