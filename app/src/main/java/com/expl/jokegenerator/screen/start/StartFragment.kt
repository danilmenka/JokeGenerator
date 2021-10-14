package com.expl.jokegenerator.screen.start

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.expl.jokegenerator.Event
import com.expl.jokegenerator.R
import com.expl.jokegenerator.Status
import com.expl.jokegenerator.databinding.FragmentStartBinding
import com.expl.jokegenerator.model.Joke
import com.expl.jokegenerator.model.SearchJokeResult
import com.expl.jokegenerator.utilits.APP_ACTIVITY
import com.expl.jokegenerator.utilits.ARGUMENT_JOKES_KEY
import com.expl.jokegenerator.utilits.showToast
import com.expl.jokegenerator.utilits.toArray
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel by viewModels<StartFragmentViewModel>()
    private lateinit var observerSearchResult: Observer<Event<SearchJokeResult>>
    private lateinit var observerRandomJoke: Observer<Event<Joke>>
    private lateinit var observerCategories: Observer<Event<List<String>>>
    private lateinit var observerSavedJokes: Observer<List<Joke>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        observeCategories()
        observeRandomJoke()
        observeSearchResult()
        mBinding.btnRandom.setOnClickListener {
            mViewModel.getRandomJokeByCategory(
                mBinding.spinner.selectedItem.toString().lowercase(Locale.getDefault())
            )
        }
        mBinding.btnSearch.setOnClickListener {
            safeGetSearchResult(mBinding.inputSearch.text.toString()){
                showToast(getString(R.string.start_fragment_valid_exception))
            }
        }
        mViewModel.getCategories()
    }

    private fun safeGetSearchResult(stringSearch: String,notValid: () -> Unit){
        if(stringSearch.length in 3..120) mViewModel.getSearchResult(stringSearch)
            else notValid()
    }

    private fun observeCategories() {
        observerCategories = Observer {
            when (it.status) {
                Status.LOADING -> processLoad()
                Status.SUCCESS -> responseCategoriesSuccess(it.data)
                Status.ERROR -> processError(it.error)
            }
        }
        mViewModel.categoriesLiveData.observe(this, observerCategories)
    }

    private fun observeRandomJoke() {
        observerRandomJoke = Observer {
            when (it.status) {
                Status.LOADING -> processLoad()
                Status.SUCCESS -> responseRandomJokeSuccess(it.data)
                Status.ERROR -> processError(it.error)
            }
        }
        mViewModel.randomJokeLivedata.observe(this, observerRandomJoke)
    }

    private fun observeSearchResult() {
        observerSearchResult = Observer {
                when (it.status) {
                    Status.LOADING -> loadSearchResultListener()
                    Status.SUCCESS -> responseSearchSuccess(it.data)
                    Status.ERROR -> processError(it.error)
                }
        }
        mViewModel.searchResultLiveData.observe(this, observerSearchResult )
    }

    private fun loadSearchResultListener() {
        mBinding.btnSearch.isClickable = false
    }

    private fun responseCategoriesSuccess(list: List<String>?) {
        list?.let { mBinding.spinner.refreshSpinner(it) }
    }

    private fun responseRandomJokeSuccess(data: Joke?) {
        mBinding.txtRandom.let {
            it.visibility = View.VISIBLE
            it.text = data?.value.toString()
        }
        mBinding.imgSaveStart.let {
            it.visibility = View.VISIBLE
            it.isClickable = true
        }
        observerSavedJokes = getSavedJokesObserver(data)
        mViewModel.savedJokes.observe(this, observerSavedJokes)
    }

    private fun getSavedJokesObserver(joke: Joke?) = Observer<List<Joke>> { jokesList ->
        joke?.let { mBinding.imgSaveStart.replaceImageButtonSave(joke in jokesList, it) }
    }

    private fun Button.replaceImageButtonSave(isSaved:Boolean, joke: Joke){
        this.let {
            if (isSaved){
                it.setBackgroundResource(R.drawable.ic_done)
                it.setOnClickListener {
                        mViewModel.delete(joke){}
                }
            }
            else{
                it.setBackgroundResource(R.drawable.ic_save_all)
                it.setOnClickListener {
                    mViewModel.saveJoke(joke){}
                    }
                }
            }
        }

    private fun responseSearchSuccess(data: SearchJokeResult?) {
        if (data?.jokesList?.isNotEmpty() == true){
        val bundle = Bundle()
        bundle.putSerializable(ARGUMENT_JOKES_KEY,data)
        mViewModel.removeSearchResult()
        APP_ACTIVITY.navController.navigate(R.id.action_viewPagerFragment_to_searchJokeFragment,bundle)
        mBinding.btnSearch.isClickable = true}
            else {
                showToast(getString(R.string.error_not_found_jokes))
                mBinding.btnSearch.isClickable = true
            }
    }

    private fun processError(error: String?) {
        showToast(error.toString())
    }

    private fun processLoad() {
        Log.e("onLoad","1")
    }

    private fun Spinner.refreshSpinner(list: List<String>){
        if (list.isNotEmpty()){
            val mutableList = list.toMutableList()
            mutableList.add(0,getString(R.string.emptySpinner))
            this.adapter = ArrayAdapter<String>(
                APP_ACTIVITY, android.R.layout.simple_spinner_item,toArray(
                    mutableList.map { it ->
                        it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                    }
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel.searchResultLiveData.removeObserver { observerSearchResult }
        mViewModel.categoriesLiveData.removeObserver { observerCategories }
        mViewModel.randomJokeLivedata.removeObserver { observerRandomJoke }
        mViewModel.savedJokes.removeObserver { observerSavedJokes }
    }
}