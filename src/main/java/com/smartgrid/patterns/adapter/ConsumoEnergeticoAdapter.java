package com.smartgrid.patterns.adapter;

import com.smartgrid.model.ConsumoEnergetico;

public interface ConsumoEnergeticoAdapter<T> {
	ConsumoEnergetico adaptar (T origen);
}
