package albek.petprojects.fooddeliveryapp.features.menu.presentation

import albek.petprojects.fooddeliveryapp.R
import albek.petprojects.fooddeliveryapp.core.base.BaseFragment
import albek.petprojects.fooddeliveryapp.core.base.ErrorModel
import albek.petprojects.fooddeliveryapp.databinding.ErrorPanelBinding
import albek.petprojects.fooddeliveryapp.databinding.FragmentMenuBinding
import albek.petprojects.fooddeliveryapp.features.menu.presentation.adapter.BannerAdapter
import albek.petprojects.fooddeliveryapp.features.menu.presentation.adapter.MealAdapter
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMenu : BaseFragment<FragmentMenuBinding, MenuViewModel>(
    FragmentMenuBinding::inflate
) {
    override val viewModel: MenuViewModel by viewModels()
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var mealAdapter: MealAdapter
    private lateinit var bannerAdapter: BannerAdapter

    override fun handleError(errorModel: ErrorModel) {
        binding.errorPanel.run {
            errorText.setText(errorModel.message)
            errorImage.setImageResource(errorModel.image)
        }
        viewModel.showError()
    }

    override fun setupListeners() {}

    override fun setupContent() {
        viewModel.initial(checkNetworkConnection())
        viewModel.state.collectLatestWithLifecycle(collector = ::updateUi)

        spinnerAdapter =
            ArrayAdapter<String>(requireContext(), R.layout.country_spinner_item, mutableListOf())
        mealAdapter = MealAdapter(click = { meal ->
            viewModel.clickOnMeal(
                getString(
                    R.string.meal_click_message,
                    meal
                )
            )
        })
        bannerAdapter =
            BannerAdapter(click = { viewModel.clickOnBoard(getString(R.string.board_click_message)) })
        binding.mealRv.adapter = mealAdapter
        binding.bannerRv.adapter = bannerAdapter
        binding.countrySpinner.adapter = spinnerAdapter
    }

    private fun updateUi(state: MenuState) {
        render(state.contentState)
        fillChips(chipContent = state.categories)
        fillAreas(areas = state.areas)
        mealAdapter.update(state.meals)
        bannerAdapter.update(state.meals.map { it.imageUrl })

    }

    private fun render(contentState: ContentState) {
        with(binding) {
            mealRv.isVisible = contentState == ContentState.CONTENT
            topAppBar.isVisible =
                contentState == ContentState.CONTENT || contentState == ContentState.EMPTY
            errorPanel.root.isVisible = contentState == ContentState.ERROR
            loadingProgress.isVisible = contentState == ContentState.LOADING
            emptyMessage.isVisible = contentState == ContentState.EMPTY
        }
    }

    private fun fillChips(chipContent: List<String>) {
        if (binding.mealCategories.childCount <= 0) {
            chipContent.forEachIndexed { index, chipText ->
                val addChip =
                    (layoutInflater.inflate(R.layout.category_chip, null, false) as Chip).apply {
                        text = chipText
                        setOnCheckedChangeListener { buttonView, isChecked ->
                            if (isChecked) {
                                viewModel.updateCategory(
                                    category = buttonView.text.toString(),
                                    isNetworkAvailable = checkNetworkConnection()
                                )
                            }
                        }
                    }
                if (index == 0) {
                    addChip.isChecked = true
                }
                binding.mealCategories.addView(addChip)
            }
        }
    }

    private fun fillAreas(areas: List<String>) {
        spinnerAdapter.clear()
        spinnerAdapter.addAll(areas)
        spinnerAdapter.notifyDataSetChanged()
        binding.countrySpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.updateArea(
                    area = areas[position],
                    isNetworkAvailable = checkNetworkConnection()
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}