package com.example.voro.baskettracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.voro.baskettracker.entities.Person;
import com.example.voro.baskettracker.entities.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterCurrentPlayers extends RecyclerView.Adapter<AdapterCurrentPlayers.ViewHolder> {


    private Team team;
    private boolean isLocal;
    private SelectedPlayer selectedPlayer;
    private Context context;
    private List<Person> players;

    public AdapterCurrentPlayers(List<Person> players, Context context, Team team, boolean b, SelectedPlayer selectedPlayer) {
        this.players = players;
        this.context = context;
        this.team = team;
        this.isLocal = b;
        this.selectedPlayer = selectedPlayer;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_player_match, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.textName.setText(players.get(viewHolder.getAdapterPosition()).getName());
        if (isLocal) {
            setLayoutLocal(viewHolder);
        } else {
            setLayoutVisitor(viewHolder);
        }
        setListeners(viewHolder);
    }

    private void setListeners(final ViewHolder viewHolder) {
        viewHolder.imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPlayer.add();
            }
        });
        viewHolder.imageRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPlayer.remove();
            }
        });
        viewHolder.imageAddVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPlayer.add();
            }
        });
        viewHolder.imageRemoveVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPlayer.remove();
            }
        });
        viewHolder.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPlayer.change(players.get(viewHolder.getAdapterPosition()));
            }
        });
    }

    private void setLayoutVisitor(ViewHolder viewHolder) {
        viewHolder.linearLocal.setVisibility(View.GONE);
        viewHolder.linearVisitor.setVisibility(View.VISIBLE);
    }

    private void setLayoutLocal(ViewHolder viewHolder) {
        viewHolder.linearLocal.setVisibility(View.VISIBLE);
        viewHolder.linearVisitor.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if (players.size() > 5) {
            return 5;
        } else {
            return players.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_add)
        ImageView imageAdd;
        @BindView(R.id.image_remove)
        ImageView imageRemove;
        @BindView(R.id.image_remove_visitor)
        ImageView imageRemoveVisitor;
        @BindView(R.id.image_add_visitor)
        ImageView imageAddVisitor;
        @BindView(R.id.linear_local)
        LinearLayout linearLocal;
        @BindView(R.id.linear_visitor)
        LinearLayout linearVisitor;
        @BindView(R.id.text_name)
        TextView textName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface SelectedPlayer {
        void change(Person person);

        void add();

        void remove();
    }
}
