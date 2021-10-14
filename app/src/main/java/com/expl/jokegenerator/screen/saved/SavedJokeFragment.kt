package com.expl.jokegenerator.screen.saved

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.expl.jokegenerator.databinding.FragmentSavedJokeBinding
import com.expl.jokegenerator.model.Joke
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedJokeFragment : Fragment() {
    private var _binding: FragmentSavedJokeBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel by viewModels<SavedJokeFragmentViewModel>()
    private lateinit var mAdapter: SavedJokeAdapter
    private lateinit var mObserverList: Observer<List<Joke>>
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedJokeBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mRecyclerView = mBinding.savedRecyclerView
        mAdapter = SavedJokeAdapter{
            deleteJoke(it)
        }
        mRecyclerView.adapter = mAdapter
        mObserverList = getObserverList()
        mViewModel.allJokes.observe(this, mObserverList)
    }

    private fun getObserverList() = Observer<List<Joke>> {jokesList ->
        setVisibilityEmptyText(jokesList.isEmpty())
        mAdapter.setList(jokesList.asReversed())
        mBinding.btnDeleteAll.setOnClickListener {
            mViewModel.deleteAll(jokesList){
                Log.e("SavedJoke","Delete All Success")
            }
        }
    }

    private fun setVisibilityEmptyText(listIsEmpty: Boolean){
        if (listIsEmpty){
            mBinding.btnDeleteAll.visibility = View.INVISIBLE
            mBinding.txtListIsEmpty.visibility = View.VISIBLE
        } else {
            mBinding.btnDeleteAll.visibility = View.VISIBLE
            mBinding.txtListIsEmpty.visibility = View.INVISIBLE
        }

    }

    private fun deleteJoke(joke: Joke){
        mViewModel.delete(joke){
            Log.d("SavedJoke","Delete Success")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mRecyclerView.adapter = null
    }
}