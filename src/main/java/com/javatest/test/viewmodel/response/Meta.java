package com.javatest.test.viewmodel.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Meta {
   private boolean success;
   private long timestamp;
}
