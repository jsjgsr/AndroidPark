package com.example.a98497.mypark;

import android.app.Fragment;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MFragment extends Fragment {
	private View view;
	private EditText edt;
	private EditText edtSearchMember;
	private GuiNumberKeyBoard keyboard;
	private Animation mHiddenAction;
	private Animation mShowAction;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_main, null);
		edt = (EditText) view.findViewById(R.id.edt);
		edtSearchMember = (EditText) view.findViewById(R.id.edtSearchMember);
		keyboard = (GuiNumberKeyBoard) view.findViewById(R.id.keyboard);
		init();
		return view;
	}

	private void init() {
		keyboard.setEditText(edt);
		edt.setInputType(InputType.TYPE_NULL);
		view.addOnLayoutChangeListener(new OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right,
					int bottom, int oldLeft, int oldTop, int oldRight,
					int oldBottom) {
				Log.d("TAG", "ddd");
			}
		});
		mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAction.setDuration(500);
		mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				1.0f);
		mHiddenAction.setDuration(500);
	}
}
