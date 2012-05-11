/**
 * 
 */
package chatonline.controller;

import java.util.List;

import chatonline.utility.AskFrd;
import chatonline.utility.Info;
import chatonline.utility.InfoWithPhoto;

/** @ClassName: HookListenClt 
 * @Description: get data from listener
 * @author Li 
 * @date 2012-4-22 ионГ01:45:22 
 * 
 */
public interface HookListenClt {
	public void delFrd(int aid);
	public boolean askForFrd(AskFrd aask);
	public void sentInfo(Info ainfo);
	public void getAskFrdRespond(int aid,boolean ais);
	public void getPhotos(List<InfoWithPhoto> alist);
	public void afterAskFrd(int aid,boolean ais);
}
