package com.example.totalhealth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [FoodListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodListFragment : Fragment() {

    private val foodItems = mutableListOf<FoodItemEntity>()
    private lateinit var foodItemsRecyclerView: RecyclerView
    private lateinit var foodItemsAdapter: FoodItemsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO:Refactor: use viewBinding?
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_food_list, container,
            false
        ).also { view ->
            foodItemsRecyclerView =
                view.findViewById<RecyclerView>(
                    R.id
                        .recycler_view_food_items
                ).apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    foodItemsAdapter = FoodItemsAdapter(foodItems)
                    adapter = foodItemsAdapter
                }
            view.findViewById<Button>(R.id.button_add_food).setOnClickListener {
                startActivity(Intent(requireActivity(), AddFoodItemActivity::class.java))
            }
            view.findViewById<Button>(R.id.button_add_water).setOnClickListener {
                startActivity(Intent(requireActivity(), AddWaterEntryActivity::class.java))
            }

        }


    }

    /** *Update Displayed FoodItems from display */
    private fun updateFoodItems() {
        val db = (requireActivity().application as? HealthApplication)?.db
        lifecycleScope.launch(IO) {
            launch {
                db?.foodItemDao()?.getAll()?.collect { items ->
                    this@FoodListFragment.foodItems.clear()
                    this@FoodListFragment.foodItems.addAll(items)
                    Log.d("FoodListFragment", "updateFoodItems:")
                    foodItemsAdapter.notifyItemRangeChanged(
                        0,
                        items.size
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateFoodItems()
    }


        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @return A new instance of fragment FoodListFragment.
             */
            @JvmStatic
            fun newInstance(): FoodListFragment {
                Log.d("FoodListFragment", "newInstance")
                return FoodListFragment()

            }
        }
    }