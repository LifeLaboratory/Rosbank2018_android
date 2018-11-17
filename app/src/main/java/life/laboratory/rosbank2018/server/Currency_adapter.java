package life.laboratory.rosbank2018.server;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;

import life.laboratory.rosbank2018.R;

public class Currency_adapter extends BaseAdapter {

    private String[] arrayList = null;
    private LayoutInflater lInflater = null;

    public Currency_adapter(Context context, String[] arr) {
        arrayList = Arrays.copyOf(arr, arr.length);
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.length;
    }

    @Override
    public Object getItem(int position) {
        return (Object) arrayList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.card_layout, parent, false);
        }

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.currency)).setText(arrayList[position].split("#")[1]);
        ((TextView) view.findViewById(R.id.currency_price)).setText(arrayList[position].split("#")[2]);

        return view;
    }
}
