package org.michaelbel.todo.ui.edit

import android.Manifest
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.platform.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import org.michaelbel.todo.R
import org.michaelbel.todo.data.entity.Priority
import org.michaelbel.todo.data.entity.TodoData
import org.michaelbel.todo.databinding.EditFragmentBinding
import org.michaelbel.todo.ui.base.BaseFragment
import org.michaelbel.todo.utils.argumentDelegate
import org.michaelbel.todo.utils.hideKeyboard
import org.michaelbel.todo.utils.shakeView
import org.michaelbel.todo.utils.showKeyboard
import javax.inject.Inject

@AndroidEntryPoint
class EditFragment: BaseFragment(R.layout.edit_fragment) {

    companion object {
        fun newInstance(it: TodoData? = null): EditFragment {
            return EditFragment().apply {
                arguments = bundleOf("todo" to it)
            }
        }
    }

    @Inject lateinit var vibrator: Vibrator

    private val todo: TodoData? by argumentDelegate()
    private val viewModel: EditViewModel by viewModels()

    private var _binding: EditFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = EditFragmentBinding.bind(view)

        binding.toolbar.setTitle(if (todo != null) R.string.edit_todo else R.string.add_todo)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_done) {
                updateItem()
            }
            true
        }

        binding.scrollChildView.setOnClickListener {
            binding.descriptionEditText.showKeyboard()
        }

        todo?.let {
            binding.titleEditText.setText(it.title)
            binding.descriptionEditText.setText(it.description)
            binding.radioGroup.check(when (it.priority) {
                Priority.LOW -> R.id.priorityLowRadio
                Priority.MEDIUM -> R.id.priorityMediumRadio
                Priority.HIGH -> R.id.priorityHighRadio
            })
        }

        viewModel.toastEvent.subscribe {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.autoDispose()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            if (todo == null) {
                delay(100L)
                binding.descriptionEditText.showKeyboard()
            }
        }
    }

    override fun onDestroyView() {
        requireActivity().hideKeyboard()
        _binding = null
        super.onDestroyView()
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun updateItem() {
        val title: String = binding.titleEditText.text.toString()
        val description: String = binding.descriptionEditText.text.toString()
        val time: Long = System.currentTimeMillis()
        val priority: Priority = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.priorityLowRadio -> Priority.LOW
            R.id.priorityMediumRadio -> Priority.MEDIUM
            R.id.priorityHighRadio -> Priority.HIGH
            else -> Priority.LOW
        }

        if (title.isNotEmpty() && description.isNotEmpty()) {
            if (todo != null) {
                todo?.let {
                    viewModel.updateTodo(TodoData(it.id, title, description, priority, it.time))
                }
            } else {
                viewModel.insertTodo(TodoData(0, title, description, priority, time))
            }
            requireActivity().hideKeyboard()
            parentFragmentManager.popBackStack()
        } else {
            when {
                title.isEmpty() -> {
                    binding.titleEditText.shakeView(2F, 0)
                    Toast.makeText(requireContext(), R.string.error_title_empty, Toast.LENGTH_SHORT).show()
                }
                description.isEmpty() -> {
                    binding.descriptionEditText.shakeView(2F, 0)
                    Toast.makeText(requireContext(), R.string.error_description_empty, Toast.LENGTH_SHORT).show()
                }
            }
            vibrator.vibrate(200L)
        }
    }
}