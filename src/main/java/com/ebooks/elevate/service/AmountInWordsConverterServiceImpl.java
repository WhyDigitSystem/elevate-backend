package com.ebooks.elevate.service;

import org.springframework.stereotype.Service;

@Service
public class AmountInWordsConverterServiceImpl implements AmountInWordsConverterService{

	private static final String[] units = {
		    "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", 
		    "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", 
		    "Eighteen", "Nineteen"
		};

		private static final String[] tens = {
		    "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
		};

		private static String convertBelowThousand(int number) {
		    if (number < 20) {
		        return units[number];
		    } else if (number < 100) {
		        return tens[number / 10] + (number % 10 != 0 ? " " + units[number % 10] : "");
		    } else {
		        return units[number / 100] + " Hundred" + 
		               (number % 100 != 0 ? " " + convertBelowThousand(number % 100) : "");
		    }
		}

		public String convert(Long amount) {
		    if (amount == 0) {
		        return "Zero Only";
		    }

		    long number = amount;
		    StringBuilder result = new StringBuilder();

		    int crore = (int) (number / 1_00_00_000);
		    number %= 1_00_00_000;

		    int lakh = (int) (number / 1_00_000);
		    number %= 1_00_000;

		    int thousand = (int) (number / 1_000);
		    number %= 1_000;

		    int hundred = (int) (number);

		    if (crore > 0) {
		        result.append(convertBelowThousand(crore)).append(" Crore ");
		    }
		    if (lakh > 0) {
		        result.append(convertBelowThousand(lakh)).append(" Lakh ");
		    }
		    if (thousand > 0) {
		        result.append(convertBelowThousand(thousand)).append(" Thousand ");
		    }
		    if (hundred > 0) {
		        result.append(convertBelowThousand(hundred));
		    }

		    return result.toString().trim() + " Only";
		}

}
