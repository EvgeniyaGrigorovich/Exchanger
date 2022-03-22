package com.devgenius.exchanger.data.converters

import com.devgenius.exchanger.data.entity.SymbolsDTO
import com.devgenius.exchanger.domain.entity.Symbols
import com.devgenius.exchanger.utils.OneWayConverter

/**
 * Конвертер из [SymbolsDTO] в [Symbols]
 *
 * @author Evgeniia Grigorovich
 **/
class SymbolsDTOToSymbolsConverter : OneWayConverter<SymbolsDTO, List<String>> {

    override suspend fun convert(from: SymbolsDTO): List<String> {
        val symbolsList = mutableListOf<String>()
        val symbols = from.symbols

        for (symbol in symbols) {
            val newSymbol = symbol.key
            symbolsList.add(newSymbol)
        }

        return symbolsList
    }
}