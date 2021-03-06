package com.whty.zjrcpos.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whty.zjrcpos.R;


/**
 * @author http://blog.csdn.net/finddreams
 * @Description:gridview的Adapter
 */
public class AccountTransAdapter extends BaseAdapter {
    private Context mContext;

    public String[] img_text = {"账户充值", "余额查询", "挂失充值",
            "补助发放", "圈存", "脱机清算"};
    public int[] imgs = {R.drawable.app_charge,
            R.drawable.app_charge_record_check,
            R.drawable.app_hanging_solutions,
            R.drawable.app_loss_registration,
            R.drawable.app_consume_record_check,
            R.drawable.app_feedback};

    public AccountTransAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_grid, parent, false);
        }
        TextView tv = get(convertView, R.id.tv_item);
        ImageView iv = get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

        tv.setText(img_text[position]);
        return convertView;
    }

    /**
     * @author http://blog.csdn.net/finddreams
     * @Description:万能的viewHolder
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
