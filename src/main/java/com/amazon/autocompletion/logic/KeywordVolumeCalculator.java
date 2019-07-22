package com.amazon.autocompletion.logic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class KeywordVolumeCalculator {

    private List<String> listSortedVolumes;
    private String keyword;
    private int volume;

    public KeywordVolumeCalculator(List<String> listSortedVolumes, String keyword) {
        this.listSortedVolumes = listSortedVolumes;
        this.keyword=keyword;
    }

    public int computeKeyWordVolume(){
        int volume = 100;
        for(String value : listSortedVolumes){
            if(this.keyword.equals(value)){
                return volume;
            }
            volume = volume - 10;
        }
        return 0;
    }


    public int getVolume(){
        return this.computeKeyWordVolume();
    }
}
