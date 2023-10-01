package jessica.module;

import java.util.ArrayList;
import java.util.List;
import jessica.Wrapper;
import jessica.clickgui.elements.NumberElement;
import jessica.events.ClientTickEvent;
import jessica.events.InputUpdateEvent;
import jessica.events.LivingJumpEvent;
import jessica.events.PlayerTickEvent;
import jessica.events.RenderGameOverlayEvent;
import jessica.events.RenderWorldLastEvent;
import jessica.utils.ConnectionUtils;
import jessica.value.Value;
import net.minecraft.network.*;

public class Module {
	private List<Value> values;
    private String name;
    protected boolean toggled;
    private Category category;
    private int keyBind;
    protected boolean shown;
    
    public Module(String m, Category c) {
    	this.values = new ArrayList<Value>();
    	this.shown = true;
    	this.keyBind = -1;
        this.name = m;
        this.category = c;
        this.toggled = false;
    }
    
    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
        try {
            Wrapper.getFiles().saveModules();
        }
        catch (Exception ex) {}
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public String getAlias() {
        return this.getName().toLowerCase().replace(" ", "");
    }
    
    public String getDescription() {
        return null;
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        try {
        	Wrapper.getFiles().saveModules();
        }
        catch (Exception ex) {}
    }
    
    public boolean isShown() {
        return this.shown;
    }

    public void setShow(boolean shown) {
        this.shown = shown;
    }
    
    public int getKeyBind() {
        return this.keyBind;
    }
    
    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }
    
    public boolean hasBind() {
        return this.getKeyBind() != -1;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<Value> getValues() {
        return this.values;
    }
    
    public void addValue(Value value) {
        this.values.add(value);
    }
    
    public void onSliderChange(NumberElement numberElement) {
    }
    
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent) {   	
    }
    
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent, Object object) {  	
    }
    
    public void onRenderGameOverlay(float partialTicks) {  	
    }
    
    public boolean onPacket(Object object, ConnectionUtils.Side side) {
        return true;
    }
    
    public void onClientTick(ClientTickEvent clientTickEvent) { 	
    }
    
    public void onPlayerTick(PlayerTickEvent playerTickEvent) { 	
    }
    
    public void onInputUpdate(InputUpdateEvent inputUpdateEvent) {   	
    }
    
    public void onJumpEvent(LivingJumpEvent livingJumpEvent) {   	
    }
}
