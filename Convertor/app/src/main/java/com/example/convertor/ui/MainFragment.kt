package com.example.convertor.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.convertor.databinding.FragmentMainBinding
import com.example.convertor.network.model.*
import com.example.convertor.ui.viewmodel.MainVM
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by viewModels<MainVM>()
    private lateinit var binding: FragmentMainBinding
    private var cursOne = 0
    private var cursTwo = 11

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var valCurs: ValCurs
        val valute: MutableList<Valute> = mutableListOf()

        viewModel.valCurs.observe(viewLifecycleOwner) {
            valCurs = it
            binding.constraint.visibility = View.VISIBLE
            binding.progress.visibility = View.INVISIBLE
            valute.addAll(valCurs.valute)
            valute.add(
                0,
                Valute(
                    "R00000",
                    NumCode("643"),
                    CharCode("RUB"),
                    Nominal("1"),
                    Name("Российский рубль"),
                    Value("1")
                )
            )

            binding.button1.setOnClickListener { selectValute(valute, 1) }
            binding.button2.setOnClickListener { selectValute(valute, 2) }

            binding.textView.text = valute[cursOne].charCode.name
            binding.textView2.text = valute[cursTwo].charCode.name
            binding.editTextNumberDecimal1.setText(
                ((valute[cursTwo].value.name.replace(
                    ",",
                    "."
                ).toFloat() * 100).roundToInt() / 100.0).toString()
            )
            binding.editTextNumberDecimal2.setText("1")

            binding.editTextNumberDecimal1.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    binding.editTextNumberDecimal2.setText(
                        calculateCurs(
                            valute,
                            binding.editTextNumberDecimal1.text.toString().toFloat(),
                            cursOne,
                            cursTwo
                        ).toString()
                    )
                }
                return@setOnKeyListener false
            }

            binding.editTextNumberDecimal2.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    binding.editTextNumberDecimal1.setText(
                        calculateCurs(
                            valute,
                            binding.editTextNumberDecimal2.text.toString().toFloat(),
                            cursTwo,
                            cursOne
                        ).toString()
                    )
                }
                return@setOnKeyListener false
            }

        }
        viewModel.getValute()

    }

    private fun calculateCurs(valute: List<Valute>, value1: Float, curs1: Int, curs2: Int): Float {
        val value2 = value1 / (valute[curs2].value.name.replace(",", ".")
            .toFloat() / valute[curs2].nominal.name.replace(",", ".")
            .toFloat()) * (valute[curs1].value.name.replace(",", ".")
            .toFloat() / valute[curs1].nominal.name.replace(",", ".").toFloat())
        return ((value2 * 100).roundToInt() / 100.0).toFloat()
    }

    private fun selectValute(valute: List<Valute>, check: Int) {
        val c = Array(valute.size) {
            "${valute[it].charCode.name} (${valute[it].name.name})"
        }
        val builder = AlertDialog.Builder(requireContext())
        with(builder)
        {
            setTitle("Выбор валюты")
            setItems(c) { _, which ->
                if (check == 1) {
                    cursOne = which
                    Log.d("dbbd", which.toString())
                    binding.textView.text = valute[which].charCode.name
                    binding.editTextNumberDecimal2.setText(
                        calculateCurs(
                            valute,
                            binding.editTextNumberDecimal1.text.toString().toFloat(),
                            cursOne,
                            cursTwo
                        ).toString()
                    )
                } else {
                    cursTwo = which
                    Log.d("dbbd", which.toString())
                    binding.textView2.text = valute[which].charCode.name
                    binding.editTextNumberDecimal1.setText(
                        calculateCurs(
                            valute,
                            binding.editTextNumberDecimal2.text.toString().toFloat(),
                            cursTwo,
                            cursOne
                        ).toString()
                    )
                }

            }
            setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
                Log.d("dbbd", "cancel")
            }
            show()
        }
    }

}