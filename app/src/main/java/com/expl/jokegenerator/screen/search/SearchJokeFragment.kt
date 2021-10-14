package com.expl.jokegenerator.screen.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.expl.jokegenerator.R
import com.expl.jokegenerator.databinding.FragmentSearchJokeBinding
import com.expl.jokegenerator.model.Joke
import com.expl.jokegenerator.model.SearchJokeResult
import com.expl.jokegenerator.model.IsJokeSaved
import com.expl.jokegenerator.utilits.ARGUMENT_JOKES_KEY
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchJokeFragment : Fragment() {
    private val mViewModel by viewModels<SearchJokeFragmentViewModel>()
    private var _binding : FragmentSearchJokeBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mSearchJokeResult: SearchJokeResult
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: SearchJokeAdapter
    private var searchResultJokesList = emptyList<Joke>()
    private lateinit var mSavedObserverList: Observer<List<Joke>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchJokeBinding.inflate(inflater,container,false)
        try {
            mSearchJokeResult = arguments?.getSerializable(ARGUMENT_JOKES_KEY) as SearchJokeResult
            searchResultJokesList = mSearchJokeResult.jokesList
        }
        catch (e:Exception){
            Log.e("SearchJoke", "Don't find argument")
        }
        return mBinding.root
    }
    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mAdapter = SearchJokeAdapter(
            { saveJoke(it) },
            { deleteJoke(it) })
        mRecyclerView = mBinding.searchRecyclerView
        mRecyclerView.adapter = mAdapter
        mSavedObserverList = getObserverList(searchResultJokesList)
        mViewModel.savedJokes.observe(this, mSavedObserverList)
    }

    private fun getObserverList(newJokesList: List<Joke>) = Observer<List<Joke>> { oldJokesList ->
        var isAllSaved = true
        val isJokeSavedList = getIsJokeSavedList(newJokesList,oldJokesList){isAllSaved = it}
        mBinding.imgSaveAll.replaceAppointment(isAllSaved,newJokesList)
        mAdapter.setList(isJokeSavedList)
    }

    private fun getIsJokeSavedList(newJokesList: List<Joke>,
                                   oldJokesList: List<Joke>,
                                   setIsAllSaved: (Boolean) -> Unit):List<IsJokeSaved>{
        val isJokeSavedList = mutableListOf<IsJokeSaved>()
        newJokesList.forEach {
            if(it in oldJokesList){
                isJokeSavedList.add(IsJokeSaved(it,true))
            }
            else{
                isJokeSavedList.add(IsJokeSaved(it))
                setIsAllSaved(false)
            }
        }
        return isJokeSavedList
    }

    private fun FloatingActionButton.replaceAppointment(isAllSaved: Boolean, jokesList: List<Joke>) {
        this.let {
            if(isAllSaved){
                it.setImageResource(R.drawable.ic_done_all)
                it.setOnClickListener {
                    deleteAllJokes(jokesList)
                }
            } else {
                it.setImageResource(R.drawable.ic_save)
                it.setOnClickListener {
                    saveAllJokes(jokesList)
                }
            }
        }
    }

    private fun deleteJoke(joke: Joke){
        mViewModel.delete(joke) {
            Log.d("SearchJoke","Delete Joke Success")
        }
    }
    private fun saveJoke(joke: Joke){
        mViewModel.insert(joke) {
            Log.d("SearchJoke","Save Joke Success")
        }
    }
    private fun saveAllJokes(jokesList: List<Joke>){
        mViewModel.saveAll(jokesList) {
            Log.d("SearchJoke","Save All Joke Success")
        }

    }
    private fun deleteAllJokes(jokesList: List<Joke>) {
        mViewModel.deleteAll(jokesList) {
            Log.d("SearchJoke", "Delete All Joke Success")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mRecyclerView.adapter = null
        mViewModel.savedJokes.removeObserver(mSavedObserverList)
    }
}


