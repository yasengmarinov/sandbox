package minumum_loss;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author Yasen Marinov
 * @since 31/07/2021
 */
public class Solution {

  public static int minimumLoss(List<Long> price) {
    TreeMap<Long, Integer> priceMap = new TreeMap<>();
    for (int i = 0; i < price.size(); i++) {
      priceMap.put(price.get(i), i);
    }

    Iterator<Long> iterator = priceMap.navigableKeySet().iterator();

    long minLoss = Long.MAX_VALUE;
    while (iterator.hasNext()) {
      long buyPrice = iterator.next();
      int buyMonth = priceMap.get(buyPrice);

      Entry<Long, Integer> sellEntryOffer = priceMap.lowerEntry(buyPrice);
      long loss = -1;
      while (sellEntryOffer != null && loss < 0) {
        if (sellEntryOffer.getValue() > buyMonth) {
          if (buyPrice > sellEntryOffer.getKey()) {
            loss = buyPrice - sellEntryOffer.getKey();
          }
        }
        sellEntryOffer = priceMap.lowerEntry(sellEntryOffer.getKey());
      }

      if (loss > 0) {
        minLoss = Math.min(loss, minLoss);
      }
    }

    return Integer.parseInt(Long.toString(minLoss));
  }

  public static void main(String... args) {
    System.out.println(minimumLoss(Arrays.asList(20L, 7L, 8L, 2L, 5L)));
  }
}
