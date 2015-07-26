package apriori;

import helper.DataHelper;
import helper.FileHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import valueobjects.AssociationObject;
import valueobjects.LargeItemSetVO;

public class Apriori {
        /*Hold the resultant large itemset */
        Set<Set<String>> largeItemSet;
        Set<Set<String>> currentItemSet;
        
        /*Maps the file into memory */
        Map<Integer, Set<String>> fileMap;
        
        public Apriori() {
                largeItemSet = new HashSet<Set<String>>();
                fileMap = new HashMap<Integer, Set<String>>();
                currentItemSet = new HashSet<Set<String>>();
        }
        
        /**
         * Does the Apriori Algo
         * @param minSupport
         * @param minConfidence
         * @throws IOException 
         */
        public void doApriori(Double minSupport, Double minConfidence, String filename) throws IOException {
                fileMap = FileHelper.parseFile(filename);
                currentItemSet = DataHelper.getLOneSet();
                largeItemSet.addAll(currentItemSet);
                while(currentItemSet.size() > 0) {
                        currentItemSet = getNextLevelItemSet(currentItemSet);
                }
                System.out.println("==Large itemsets ( min_support="+ minSupport*100 + "% )");
            //    printLargeItemSet();
                System.out.println();
                System.out.println("==High-confidence association rules"+ "( min_conf="+minConfidence*100 + "% )");
                generateAssociationRules(minConfidence);
        }
        
        /**
         * private method to generate association rules
         */
        private void generateAssociationRules(Double minConfidence) {
                ArrayList<AssociationObject> associations = new ArrayList<AssociationObject>();
                ArrayList<LargeItemSetVO> set = new ArrayList<LargeItemSetVO>(DataHelper.getLargeItemSetWithSupport());
                for(LargeItemSetVO vo: set) {
                        /* Get one item on the rhs */
                        if (vo.getItems().size() == 1) {
                                String item = vo.getItems().iterator().next();
                                for (LargeItemSetVO other : set) {
                                        if (other.getItems().contains(item) && other.getItems().size() > 1) {
                                                /*Propose an association if > than confidence*/
                                                
                                                Set<String> toOutput = new HashSet<String>(other.getItems());
                                                toOutput.remove(item);
                                                if ((other.getSupport().doubleValue() / DataHelper.getSupport(toOutput).doubleValue()) >= minConfidence) {
                                                        associations.add(new AssociationObject(toOutput, vo.getItems(), (other.getSupport().doubleValue() / DataHelper.getSupport(toOutput).doubleValue()), other.getSupport()));
                                                }
                                        }
                                }
                        }
                }
                Collections.sort(associations);
                printAssociations(associations);
        }
        
        private void printAssociations(ArrayList<AssociationObject> associations) {
                for (AssociationObject obj: associations) {
                        System.out.println(obj.getLhs() + " => "+ obj.getRhs() + " (conf:"+obj.getConfidence()*100+ "% supp:" + obj.getSupport()*100+ "% )");
                }
        }

        public void printLargeItemSet() {
                ArrayList<LargeItemSetVO> set = new ArrayList<LargeItemSetVO>(DataHelper.getLargeItemSetWithSupport());
                Collections.sort(set);
                for(LargeItemSetVO vo: set) {
                        System.out.println(vo.getItems().toString() + ", " + vo.getSupport()*100+"%" );
                }
        }
        
        public Set<Set<String>> getNextLevelItemSet(Set<Set<String>> curItemSet){
                Set<Set<String>> cloneSet = new HashSet<Set<String>>(curItemSet);
                Set<Set<String>> nextLevel = new HashSet<Set<String>>();
                
                for(Set<String> set : curItemSet) {
                        for(Set<String> set1 : cloneSet){
                                if (!set.equals(set1)) {
                                        Set<String> toAdd = new HashSet<String>(set);
                                        toAdd.addAll(set1);
                                        nextLevel.add(toAdd);
                                }
                        }
                }
                nextLevel = DataHelper.getFrequentItems(nextLevel, fileMap);
                largeItemSet.addAll(nextLevel);
                return nextLevel;
        }
        
}

