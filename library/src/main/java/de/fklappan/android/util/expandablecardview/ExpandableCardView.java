package de.fklappan.android.util.expandablecardview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class ExpandableCardView extends CardView {

    private static final String TAG = ExpandableCardView.class.getSimpleName();

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // callback definition

    public interface OnExpandListener {
        void onExpand();
    }

    public interface OnCollapseListener {
        void onCollapse();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // member variables

    private OnCollapseListener onCollapseListener = new OnCollapseListener() {
        @Override
        public void onCollapse() {
            // do nothing, just avoid NPE
        }
    };

    private OnExpandListener onExpandListener = new OnExpandListener() {
        @Override
        public void onExpand() {
            // do nothing, just avoid NPE

        }
    };
    private TypedArray attributes;
    private FrameLayout frameLayoutUserContent;
    private ImageView imageViewExpand;
    private TextView textViewHeader;
    private ColorStateList defaultHeaderTextColors;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // initialisation

    public ExpandableCardView(@NonNull Context context) {
        super(context);
        throw new IllegalArgumentException("Please provide xml property 'app:headerText='");
    }

    public ExpandableCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        // store attributes - we cant use them now, must wait till be layout is complete
        attributes = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableCardView);

        View.inflate(context, R.layout.expandable_card_view, this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // android base overrides

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        View defaultContent = inflate(getContext(), R.layout.default_content, this);
        defaultContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected()) {
                    frameLayoutUserContent.setVisibility(GONE);
                    imageViewExpand.animate().rotation(0).start();
                    onCollapseListener.onCollapse();
                } else {
                    frameLayoutUserContent.setAlpha(0);
                    frameLayoutUserContent.animate().alpha(1).start();
                    frameLayoutUserContent.setVisibility(VISIBLE);
                    imageViewExpand.animate().rotation(-180).start();
                    onExpandListener.onExpand();
                }
                setSelected(!isSelected());
            }
        });

        textViewHeader = defaultContent.findViewById(R.id.textViewHeader);
        textViewHeader.setText(attributes.getString(R.styleable.ExpandableCardView_headerText));
        defaultHeaderTextColors = textViewHeader.getTextColors();

        imageViewExpand = defaultContent.findViewById(R.id.imageButtonExpand);

        frameLayoutUserContent = defaultContent.findViewById(R.id.frameLayoutContent);

        // remove user provided layout from our view
        View view = getChildAt(1);
        removeViewAt(1);

        // and put it into our content layout
        frameLayoutUserContent.addView(view);
        frameLayoutUserContent.setVisibility(GONE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // member methods

    /**
     * Sets the tint color for the expandable icon
     * @param color - a
     */

    public void setExpandIconColor(int color) {
        imageViewExpand.setColorFilter(color);
    }

    public void setHeaderTextColor(int color) {
        textViewHeader.setTextColor(color);
    }

    public void resetHeaderTextColor() {
        textViewHeader.setTextColor(defaultHeaderTextColors);
    }

    public void setOnCollapseListener(OnCollapseListener onCollapseListener) {
        this.onCollapseListener = onCollapseListener;
    }

    public void setOnExpandListener(OnExpandListener onExpandListener) {
        this.onExpandListener = onExpandListener;
    }

}
