package albek.petprojects.fooddeliveryapp.core.base

import albek.petprojects.fooddeliveryapp.R
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.io.IOException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

abstract class BaseViewModel : ViewModel() {
    private val viewCommandChannel = MutableSharedFlow<ViewCommand>()
    val commandsFlow: SharedFlow<ViewCommand> get() = viewCommandChannel
    private val errorChannel = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> get() = errorChannel

    companion object {
        const val ERROR_TAG = "ERROR_TAG"
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(ERROR_TAG, throwable.stackTrace.joinToString(separator = "\n"))
        viewModelScope.launch {
            errorChannel.emit(
                when (throwable) {
                    is SourceNotFoundException -> ErrorModel(message = R.string.network_exception, image = R.drawable.network_error)
                    else -> ErrorModel(message = R.string.unknown_error, image = R.drawable.error_icon)
                }
            )
        }
    }

    fun executeCommand(viewCommand: ViewCommand) = runOnUi {
        viewCommandChannel.emit(viewCommand)
    }

    private val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)
    private val uiScope =
        CoroutineScope(SupervisorJob() + viewModelScope.coroutineContext + exceptionHandler)
    private val defaultScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + exceptionHandler)

    fun runOnUi(action: suspend () -> Unit) = uiScope.launch { action() }
    fun runOnIo(action: suspend () -> Unit) = ioScope.launch { action() }
    fun runOnDefault(action: suspend () -> Unit) = defaultScope.launch { action() }
    fun runBlockingOnIo(action: suspend () -> Unit) =
        runBlocking(SupervisorJob() + Dispatchers.IO + exceptionHandler) {
            action()
        }
}