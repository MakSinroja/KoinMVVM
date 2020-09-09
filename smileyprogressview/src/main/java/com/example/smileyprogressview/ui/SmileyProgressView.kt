package com.example.smileyprogressview.ui

import android.animation.Animator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.smileyprogressview.R
import com.example.smileyprogressview.listeners.OnAnimPerformCompletedListener
import com.example.smileyprogressview.utils.dp2px
import kotlin.math.min


class SmileyProgressView : View {

    companion object {
        const val DEFAULT_WIDTH = 30
        const val DEFAULT_HEIGHT = 30
        const val DEFAULT_PAINT_WIDTH = 5

        const val DEFAULT_ANIM_DURATION = 2000
        val DEFAULT_PAINT_COLOR = Color.parseColor("#b3d8f3")
        const val ROTATE_OFFSET = 90
    }

    private lateinit var mArcPaint: Paint
    private lateinit var mCirclePaint: Paint

    private lateinit var mCirclePath: Path
    private lateinit var mArcPath: Path

    private lateinit var mRectF: RectF

    private var mCenterPos = FloatArray(2)
    private var mLeftEyePos = FloatArray(2)
    private var mRightEyePos = FloatArray(2)

    private var mStartAngle = 0f
    private var mSweepAngle = 0f
    private var mEyeCircleRadius = 0f

    private var mStrokeColor = 0
    private var mAnimDuration = 0
    private var mAnimRepeatCount = 0

    private var mStrokeWidth = 0
    private var mRunning = false
    private var mStopping = false

    private var mFirstStep = false
    private var mShowLeftEye = false
    private var mShowRightEye = false
    private var mStopUntilAnimationPerformCompleted = false

    private var mOnAnimPerformCompletedListener: OnAnimPerformCompletedListener? = null
    private var mValueAnimator: ValueAnimator? = null

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {

        // get attrs
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SmileyProgressView)
        mStrokeColor = ta.getColor(R.styleable.SmileyProgressView_strokeColor, DEFAULT_PAINT_COLOR)
        mStrokeWidth = ta.getDimensionPixelSize(
            R.styleable.SmileyProgressView_strokeWidth,
            dp2px(context, DEFAULT_PAINT_WIDTH.toFloat())
        )
        mAnimDuration = ta.getInt(R.styleable.SmileyProgressView_duration, DEFAULT_ANIM_DURATION)
        mAnimRepeatCount =
            ta.getInt(R.styleable.SmileyProgressView_animRepeatCount, ValueAnimator.INFINITE)
        ta.recycle()

        mSweepAngle = 180f // init sweepAngle, the mouth line sweep angle

        mCirclePath = Path()
        mArcPath = Path()

        mArcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeCap = Paint.Cap.ROUND
        mArcPaint.strokeJoin = Paint.Join.ROUND
        mArcPaint.strokeWidth = mStrokeWidth.toFloat()
        mArcPaint.color = mStrokeColor

        mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mCirclePaint.style = Paint.Style.STROKE
        mCirclePaint.strokeCap = Paint.Cap.ROUND
        mCirclePaint.strokeJoin = Paint.Join.ROUND
        mCirclePaint.style = Paint.Style.FILL
        mCirclePaint.color = mStrokeColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            measureWidthSize(widthMeasureSpec),
            measureHeightSize(heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (mRunning) {
            if (mShowLeftEye) {
                canvas.drawCircle(
                    mLeftEyePos[0],
                    mLeftEyePos[1],
                    mEyeCircleRadius,
                    mCirclePaint
                )
            }
            if (mShowRightEye) {
                canvas.drawCircle(
                    mRightEyePos[0],
                    mRightEyePos[1],
                    mEyeCircleRadius,
                    mCirclePaint
                )
            }
            if (mFirstStep) {
                mArcPath.reset()
                mArcPath.addArc(mRectF, mStartAngle, mSweepAngle)
                canvas.drawPath(mArcPath, mArcPaint)
            } else {
                mArcPath.reset()
                mArcPath.addArc(mRectF, mStartAngle, mSweepAngle)
                canvas.drawPath(mArcPath, mArcPaint)
            }
        } else {
            canvas.drawCircle(
                mLeftEyePos[0],
                mLeftEyePos[1],
                mEyeCircleRadius,
                mCirclePaint
            )
            canvas.drawCircle(
                mRightEyePos[0],
                mRightEyePos[1],
                mEyeCircleRadius,
                mCirclePaint
            )
            mArcPath.reset()
            mArcPath.addArc(mRectF, mStartAngle, mSweepAngle)
            canvas.drawPath(mArcPath, mArcPaint)
        }
    }

    /**
     * measure width
     * @param measureSpec spec
     * @return width
     */
    private fun measureWidthSize(measureSpec: Int): Int {
        val defSize = dp2px(context, DEFAULT_WIDTH.toFloat())
        val specSize = MeasureSpec.getSize(measureSpec)
        val specMode = MeasureSpec.getMode(measureSpec)
        var result = 0
        when (specMode) {
            MeasureSpec.UNSPECIFIED, MeasureSpec.AT_MOST -> result = min(defSize, specSize)
            MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }

    /**
     * measure height
     * @param measureSpec spec
     * @return height
     */
    private fun measureHeightSize(measureSpec: Int): Int {
        val defSize = dp2px(context, DEFAULT_HEIGHT.toFloat())
        val specSize = MeasureSpec.getSize(measureSpec)
        val specMode = MeasureSpec.getMode(measureSpec)
        var result = 0
        when (specMode) {
            MeasureSpec.UNSPECIFIED, MeasureSpec.AT_MOST -> result = min(defSize, specSize)
            MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom

        val width = width
        val height = height

        mCenterPos[0] = (width - paddingRight + paddingLeft shr 1).toFloat()
        mCenterPos[1] = (height - paddingBottom + paddingTop shr 1).toFloat()

        val radiusX = (width - paddingRight - paddingLeft - mStrokeWidth shr 1.toFloat()
            .toInt()).toFloat()
        val radiusY = (height - paddingTop - paddingBottom - mStrokeWidth shr 1.toFloat()
            .toInt()).toFloat()
        val radius = min(radiusX, radiusY)

        mEyeCircleRadius = mStrokeWidth / 2.toFloat()
        mRectF = RectF(
            (paddingLeft + mStrokeWidth).toFloat(),
            (paddingTop + mStrokeWidth).toFloat(),
            (width - mStrokeWidth - paddingRight).toFloat(),
            (height - mStrokeWidth - paddingBottom).toFloat()
        )
        mArcPath.arcTo(mRectF, 0f, 180f)
        mCirclePath.addCircle(mCenterPos[0], mCenterPos[1], radius, Path.Direction.CW)

        val circlePathMeasure = PathMeasure(mCirclePath, true)
        circlePathMeasure.getPosTan(circlePathMeasure.length / 8 * 5, mLeftEyePos, null)
        circlePathMeasure.getPosTan(circlePathMeasure.length / 8 * 7, mRightEyePos, null)

        mLeftEyePos[0] += (mStrokeWidth shr 2).toFloat()
        mLeftEyePos[1] += (mStrokeWidth shr 1).toFloat()
        mRightEyePos[0] -= (mStrokeWidth shr 2).toFloat()
        mRightEyePos[1] += (mStrokeWidth shr 1).toFloat()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mValueAnimator != null && mValueAnimator?.isRunning == true) {
            mValueAnimator?.end()
        }
    }

    /**
     * Set paint color alpha
     * @param alpha alpha
     */
    fun setPaintAlpha(alpha: Int) {
        mArcPaint.alpha = alpha
        mCirclePaint.alpha = alpha
        invalidate()
    }

    /**
     * Set paint stroke color
     * @param color color
     */
    fun setStrokeColor(color: Int) {
        mStrokeColor = color
        invalidate()
    }

    /**
     * Set paint stroke width
     * @param width px
     */
    fun setStrokeWidth(width: Int) {
        mStrokeWidth = width
    }

    /**
     * Set animation running duration
     * @param duration duration
     */
    fun setAnimDuration(duration: Int) {
        mAnimDuration = duration
    }

    /**
     * Set animation repeat count, ValueAnimator.INFINITE(-1) means cycle
     * @param repeatCount repeat count
     */
    fun setAnimRepeatCount(repeatCount: Int) {
        mAnimRepeatCount = repeatCount
    }

    /**
     * set anim repeat count
     * @param animRepeatCount anim repeat count
     * value: -1 (INFINITE)
     */
    fun start(animRepeatCount: Int) {
        mAnimRepeatCount = animRepeatCount
        mFirstStep = true
        mValueAnimator = ValueAnimator.ofFloat(ROTATE_OFFSET.toFloat(), 720.0f + 2 * ROTATE_OFFSET)
        mValueAnimator?.apply {
            duration = mAnimDuration.toLong()
            interpolator = LinearInterpolator()
            repeatCount = mAnimRepeatCount
            repeatMode = ValueAnimator.RESTART

            addUpdateListener(AnimatorUpdateListener { animation ->
                if (!animation.isRunning) {
                    return@AnimatorUpdateListener
                }
                val animatedValue = animation.animatedValue as Float
                mFirstStep = animatedValue / 360.0f <= 1
                if (mFirstStep) {
                    mShowLeftEye = animatedValue % 360 > 225.0f
                    mShowRightEye = animatedValue % 360 > 315.0f

                    // set arc sweep angle when the step is first, set value: 0.1f similar to a circle
                    mSweepAngle = 0.1f
                    mStartAngle = animation.animatedValue as Float
                } else {
                    mShowLeftEye = animatedValue / 360.0f <= 2 && animatedValue % 360 <= 225.0f
                    mShowRightEye = animatedValue / 360.0f <= 2 && animatedValue % 360 <= 315.0f
                    if (animatedValue >= 720.0f + ROTATE_OFFSET) {
                        mStartAngle = animatedValue - (720.0f + ROTATE_OFFSET)
                        mSweepAngle = ROTATE_OFFSET - mStartAngle
                    } else {
                        mStartAngle =
                            if (animatedValue / 360.0f <= 1.625) 0f else animatedValue - mSweepAngle - 360
                        mSweepAngle =
                            if (animatedValue / 360.0f <= 1.625) animatedValue % 360 else 225 - (animatedValue - 225 - 360) / 5 * 3
                    }
                }
                invalidate()
            })

            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    mRunning = true
                    if (mOnAnimPerformCompletedListener != null) {
                        mOnAnimPerformCompletedListener?.onStart()
                    }
                }

                override fun onAnimationEnd(animation: Animator) {
                    mRunning = false
                    mStopping = false
                    if (mOnAnimPerformCompletedListener != null) {
                        mOnAnimPerformCompletedListener?.onCompleted()
                    }
                    reset()
                }

                override fun onAnimationCancel(animation: Animator) {
                    mRunning = false
                    mStopping = false
                    if (mOnAnimPerformCompletedListener != null) {
                        mOnAnimPerformCompletedListener?.onCompleted()
                    }
                    reset()
                }

                override fun onAnimationRepeat(animation: Animator) {
                    if (mStopUntilAnimationPerformCompleted) {
                        animation.cancel()
                        mStopUntilAnimationPerformCompleted = false
                    }
                }
            })
            start()
        }
    }

    /**
     * Start animation
     */
    fun start() {
        start(ValueAnimator.INFINITE)
    }

    /**
     * Stop animation
     */
    fun stop() {
        stop(true)
    }

    /**
     * stop it after animation perform completed
     * @param stopUntilAnimationPerformCompleted boolean
     */
    fun stop(stopUntilAnimationPerformCompleted: Boolean) {
        if (mStopping || mValueAnimator == null || mValueAnimator?.isRunning == false) {
            return
        }
        mStopping = stopUntilAnimationPerformCompleted
        mStopUntilAnimationPerformCompleted = stopUntilAnimationPerformCompleted
        if (mValueAnimator != null && mValueAnimator?.isRunning == true) {
            if (!stopUntilAnimationPerformCompleted) {
                mValueAnimator?.end()
            }
        } else {
            mStopping = false
            if (mOnAnimPerformCompletedListener != null) {
                mOnAnimPerformCompletedListener?.onCompleted()
            }
        }
    }

    /**
     * set status changed listener
     * @param l OnStatusChangedListener
     */
    fun setOnAnimPerformCompletedListener(l: OnAnimPerformCompletedListener?) {
        mOnAnimPerformCompletedListener = l
    }

    /**
     * reset UI
     */
    private fun reset() {
        mStartAngle = 0f
        mSweepAngle = 180f
        invalidate()
    }
}