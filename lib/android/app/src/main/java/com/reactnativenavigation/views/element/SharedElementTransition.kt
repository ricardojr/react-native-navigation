package com.reactnativenavigation.views.element

import android.animation.Animator
import android.animation.AnimatorSet
import android.view.View
import com.reactnativenavigation.parse.SharedElementTransitionOptions
import com.reactnativenavigation.views.element.animators.*

class SharedElementTransition(options: SharedElementTransitionOptions) : Transition() {
    val fromId: String = options.fromId.get()
    val toId: String = options.toId.get()
    lateinit var from: View
    lateinit var to: View

    fun isValid(): Boolean = this::from.isInitialized

    override fun createAnimators(): AnimatorSet {
        val animators = mutableListOf<Animator>()
        for (creator in animators()) {
            if (creator.shouldAnimateProperty()) animators.add(creator.create())
        }
        val set = AnimatorSet()
        set.playTogether(animators)
        return set
    }

    private fun animators(): List<PropertyAnimatorCreator<*>> {
        return listOf(
                XAnimator(from, to),
                YAnimator(from, to),
                MatrixAnimator(from, to),
                ScaleXAnimator(from, to),
                ScaleYAnimator(from, to),
                BackgroundColorAnimator(from, to),
                TextChangeAnimator(from, to)
        )
    }
}