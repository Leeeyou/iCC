/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.ly.cc.view.activity.custom.android5p0;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gc.materialdesign.widgets.Dialog;
import com.ly.cc.R;
import com.ly.cc.utils.T;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaterialDesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_material_design);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.button)
    public void onClickShowDialog() {
        final Dialog dialog = new Dialog(this, "Title", "创业前， 你只想努力2-3倍，从而得到相应的回报。 但真正创业后，你的竞争对手决定了你到底要有多辛苦。 而他们做出的决定都是一样的： 你能吃多少苦，我们就能吃多少苦，所以创业者才会很苦逼。\n" +
                "这种情况下，你应该超越出来，从层次上高于你所在的环境。否则你势必将做得异常累。\n" +
                "好比天天关心房子和油价，粮食和蔬菜的人，可能一辈子都只关心这个。完成超越的第一步是把眼睛移开。朝你真正热爱并能够做到卓越的领域深扎下去，进入另外一个维度。\n" +
                "有句话讲，随着技术的发展，每一代人都在做上一代人觉得很浪费的事情。 试想，如果站在百年后的一天看你的现在，你可以明显地看出你的蠢笨。时光无法企及的，思想可以走的远一些。如果你瞬间有5年后的智慧，完成思想的穿越，你就可以升华为灵魂的超越。");


        dialog.addCancelButton("取消", v -> {
            T.showShort(MaterialDesignActivity.this, "取消");
            dialog.dismiss();
        });

        // Set accept click listenner
        dialog.setOnAcceptButtonClickListener(v -> {
            T.showShort(MaterialDesignActivity.this, "确定");
            dialog.dismiss();
        });

        dialog.show();

        dialog.getButtonAccept().setText("确定");


        // Acces to accept button
        //        ButtonFlat acceptButton = dialog.getButtonAccept();
        // Acces to cancel button
        //        ButtonFlat cancelButton = dialog.getButtonCancel();
    }


}
