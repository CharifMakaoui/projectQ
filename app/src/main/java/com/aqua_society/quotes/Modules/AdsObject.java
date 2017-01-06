package com.aqua_society.quotes.Modules;

/**
 * Created by MrCharif on 29/12/2016.
 */

public class AdsObject {

    private String bannerAd;
    private String interstitialAd;
    private String nativeAd;

    public AdsObject(String interstitialAd, String bannerAd, String nativeAd) {
        this.interstitialAd = interstitialAd;
        this.bannerAd = bannerAd;
        this.nativeAd = nativeAd;
    }

    public String getInterstitialAd() {
        return interstitialAd;
    }

    public void setInterstitialAd(String interstitialAd) {
        this.interstitialAd = interstitialAd;
    }

    public String getNativeAd() {
        return nativeAd;
    }

    public void setNativeAd(String nativeAd) {
        this.nativeAd = nativeAd;
    }

    public String getBannerAd() {
        return bannerAd;
    }

    public void setBannerAd(String bannerAd) {
        this.bannerAd = bannerAd;
    }


}
