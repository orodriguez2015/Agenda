package com.oscar.agenda.decorator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

/**
 * Clase DotSpanTop que permite dibujar un círculo en la parte superior
 * de un día de MaterialCalendarview
 *
 * Created by oscar on 28/01/17.
 */
public class DotSpanTop implements LineBackgroundSpan {

    /**
     * Default radius used
     */
    public static final float DEFAULT_RADIUS = 3;

    private final float radius;
    private final int color;

    /**
     * Create a span to draw a dot using default radius and color
     *
     */
    public DotSpanTop() {
        this.radius = DEFAULT_RADIUS;
        this.color = 0;
    }

    /**
     * Create a span to draw a dot using a specified color
     *
     * @param color color of the dot
     * @see #DEFAULT_RADIUS
     */
    public DotSpanTop(int color) {
        this.radius = DEFAULT_RADIUS;
        this.color = color;
    }

    /**
     * Create a span to draw a dot using a specified radius
     * @param radius radius for the dot
     */
    public DotSpanTop(float radius) {
        this.radius = radius;
        this.color = 0;
    }

    /**
     * Create a span to draw a dot using a specified radius and color
     * @param radius radius for the dot
     * @param color color
     */
    public DotSpanTop(float radius, int color) {
        this.radius = radius;
        this.color = color;
    }


    /**
     * drawBackground
     * @param canvas
     * @param paint
     * @param left
     * @param right
     * @param top
     * @param baseline
     * @param bottom
     * @param charSequence
     * @param start
     * @param end
     * @param lineNum
     */
    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum
    ) {
        int oldColor = paint.getColor();
        if (color != 0) {
            paint.setColor(color);
        }
        canvas.drawCircle((left + right) / 2, (top + radius) -8, radius, paint);
        paint.setColor(oldColor);
    }

}