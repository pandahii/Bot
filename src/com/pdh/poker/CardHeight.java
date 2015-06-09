/**
 * www.TheAIGames.com 
 * Heads Up Omaha pokerbot
 *
 * Last update: May 07, 2014
 *
 * @author Jim van Eeden, Starapple
 * @version 1.0
 * @License MIT License (http://opensource.org/Licenses/MIT)
 */


package com.pdh.poker;

public enum CardHeight {
	TWO {
		public String toString() {
			return "2";
		}
	},
	THREE {
		public String toString() {
			return "3";
		}
	},
	FOUR {
		public String toString() {
			return "4";
		}
	},
	FIVE {
		public String toString() {
			return "5";
		}
	},
	SIX {
		public String toString() {
			return "6";
		}
	},
	SEVEN {
		public String toString() {
			return "7";
		}
	},
	EIGHT {
		public String toString() {
			return "8";
		}
	},
	NINE {
		public String toString() {
			return "9";
		}
	},
	TEN {
		public String toString() {
			return "10";
		}
	},
	JACK {
		public String toString() {
			return "J";
		}
	},
	QUEEN {
		public String toString() {
			return "Q";
		}
	},
	KING {
		public String toString() {
			return "K";
		}
	},
	ACE {
		public String toString() {
			return "A";
		}
	};
}
