package albek.petprojects.fooddeliveryapp.core.base

import albek.petprojects.fooddeliveryapp.core.util.checkNetwork
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment<out BINDING: ViewBinding, out VM: BaseViewModel>(
    private val bindingInflater: (LayoutInflater) -> BINDING
): Fragment() {
    private var mutableBinding: BINDING? = null
    val binding get() = mutableBinding!!

    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mutableBinding = bindingInflater(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.commandsFlow.collectLatestWithLifecycle { handleCommand(it) }
        viewModel.errorFlow.collectLatestWithLifecycle { handleError(it) }
        setupContent()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        destroy()
        mutableBinding = null
    }

    protected open fun handleCommand(command: ViewCommand) {
        when(command) {
            is ShowSnackBar -> showSnackBar(command)
        }
    }

    abstract fun handleError(errorModel: ErrorModel)
    abstract fun setupListeners()
    abstract fun setupContent()
    // Вызывается в onDestroyView вместе с уничтожением биндинга,
    // поэтому желательно к применению вместо onDestroyView
    protected open fun destroy() {}

    fun<T> Flow<T>.collectWithLifecycle(
        state: Lifecycle.State = Lifecycle.State.STARTED,
        collector: (T) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state) {
                collect { flowData: T -> collector(flowData) }
            }
        }
    }

    fun<T> Flow<T>.collectLatestWithLifecycle(
        state: Lifecycle.State = Lifecycle.State.STARTED,
        collector: (T) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state) {
                collectLatest { flowData: T -> collector(flowData) }
            }
        }
    }

    private fun showSnackBar(command: ShowSnackBar) {
        val snackMessage = command.messageId?.let {
            requireContext().getString(it)
        } ?: command.message ?: ""
        Snackbar.make(requireView(), snackMessage, Snackbar.LENGTH_SHORT).show()
    }

    fun checkNetworkConnection(): Boolean = requireContext().checkNetwork()
}