package tn.rnu.isetr.tp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tn.rnu.isetr.tp.Entity.User;
import tn.rnu.isetr.tp.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<User> userList;
    private final Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.textUserName.setText(user.getName());
        holder.textUserContact.setText(user.getEmail());

        holder.buttonCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + user.getPhoneNumber()));
            context.startActivity(intent);
        });

        holder.buttonSMS.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:" + user.getPhoneNumber()));
            context.startActivity(intent);
        });

        holder.buttonEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + user.getEmail()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textUserName, textUserContact;
        Button buttonCall, buttonSMS, buttonEmail;
        public UserViewHolder(View itemView) {
            super(itemView);
            textUserName = itemView.findViewById(R.id.textUserName);
            textUserContact = itemView.findViewById(R.id.textUserContact);
            buttonCall = itemView.findViewById(R.id.buttonCall);
            buttonSMS = itemView.findViewById(R.id.buttonSMS);
            buttonEmail = itemView.findViewById(R.id.buttonEmail);
        }
    }
}
