package com.bidjee.digitalpokerchips.v;


public class HostRenderer {
	/*
	public static final int chip_img_height = 252;
	
	Renderer renderer;
	
	
	
	public HostRenderer(Renderer renderer_) {
		renderer=renderer_;
	}
	
	public void render() {
		
		renderShadowsToBuffer();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		renderer.spriteBatch.begin();
		
		renderBackground();
		
		renderShadows();
		
		renderChairs();
		
		if (renderer.mHS.gameStage==HostScreen.STAGE_SELECT_WINNERS_SPLIT)
			renderSpotlightsSplit();
		
		renderPlayers();
		if (renderer.mHS.gameStage!=HostScreen.STAGE_LOBBY) {
			renderer.spriteBatch.end();
			renderer.mHS.activity.getITextRenderer().renderPlayerNames();
			renderer.spriteBatch.begin();
		}
		
		renderWinStacks();
		
		if (renderer.mHS.pots.size()>0)
			renderPot();
		
		renderer.spriteBatch.end();
		renderer.mHS.activity.getITextRenderer().renderPotTotal();
		renderer.spriteBatch.begin();
		
		renderBetStacks();
		
		renderBettingStacks();
		
		renderButtons();
		
		renderer.spriteBatch.end();
		renderer.mHS.activity.getITextRenderer().renderPromptsUnderMenu();
		renderer.spriteBatch.begin();
		
		renderMenuTable();
		
		renderer.spriteBatch.end();
		renderer.mHS.activity.getITextRenderer().renderMenu();
		renderer.mHS.activity.getITextRenderer().renderPrompts();
		renderer.spriteBatch.begin();
		
		if (renderer.mHS.dealerChip.isOnScreen)
			renderDealerChip();
		
		renderFlingDemo();
		
		renderForegroundPlayers();
		
		renderFadeIn();
		
		renderer.spriteBatch.end();
		
	}
	
	private void renderShadowsToBuffer() {
		
		renderer.spriteBatch.setBlendFunction(GL11.GL_ONE,GL11.GL_ONE);
		renderer.spriteBatch.setProjectionMatrix(renderer.screenMatrix);
		renderer.occludersFBO.begin();
		renderer.spriteBatch.begin();
		Gdx.gl.glClearColor(0f,0f,0f,0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		for (int seat_=0;seat_<HostScreen.NUM_SEATS;seat_++) {
			Player player_=renderer.mHS.seats[seat_].player;
			if (player_!=null) {
				if (player_.bettingStack.size()>0) {
					renderStackShadow(player_.bettingStack);
				}
				if (player_.winStack.size()>0) {
					renderStackShadow(player_.winStack);
				}
				if (player_.betStack.size()>0) {
					int numPooling_=renderStackShadow(player_.betStack);
					if (renderer.mHS.gameStage==HostScreen.STAGE_WAIT_POOL_STACKS) {
						int chipIndex_=player_.betStack.renderSize();
						int maxRenderNum_=chipIndex_;
						for (;chipIndex_<player_.betStack.size()&&numPooling_<maxRenderNum_;chipIndex_++) {
							Chip thisChip_=player_.betStack.get(chipIndex_);
							if (thisChip_.pooling) {
								int radiusX0_=(int) (Chip.radiusX*renderer.shadowDownsampleFactor*1.1f);
								int radiusY0_=(int) (Chip.radiusY*renderer.shadowDownsampleFactor*1.1f);
								float zxOffset_=Chip.Z_X_OFFSET_RATIO*radiusX0_;
								float zyOffset_=Chip.Z_Y_OFFSET_RATIO*radiusY0_;
								renderer.spriteBatch.draw(renderer.shadowTexture,
										thisChip_.x*renderer.shadowDownsampleFactor-radiusX0_+zxOffset_*thisChip_.z,
										thisChip_.y*renderer.shadowDownsampleFactor-radiusY0_+zyOffset_*thisChip_.z,
										radiusX0_*2,radiusY0_*2,
										0,0,32,32,false,false);
								numPooling_++;
							}
						}
					}
				}
			}
		}
		if (renderer.mHS.pots.size()>0) {
			ChipStack stack_=renderer.mHS.pots.get(renderer.mHS.displayedPotIndex).potStack;
			if (renderer.mHS.gameStage==HostScreen.STAGE_SPLITTING_POTS&&stack_.pastMax()) {
				renderPotStackShadow(stack_);
			} else {
				renderStackShadow(stack_);
			}
			Arrays.fill(potChipsRendered,0);
		}
		renderer.spriteBatch.end();
		renderer.occludersFBO.end();
		renderer.spriteBatch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		renderer.spriteBatch.setProjectionMatrix(renderer.viewMatrix);
	}
	
	private int renderStackShadow(ChipStack stack_) {
		int numStack_=stack_.renderSize();
		int numPooling_=0;
		int N=0;
		float zxOffset_;
		float zyOffset_;
		int radiusX0_=(int) (Chip.radiusX*renderer.shadowDownsampleFactor*1.1f);
		int radiusY0_=(int) (Chip.radiusY*renderer.shadowDownsampleFactor*1.1f);
		for (;N<numStack_;N++) {
			Chip thisChip_=stack_.get(N);
			float rotation_=thisChip_.rotation;
			if (rotation_==-90||rotation_==270) {
				zxOffset_=Chip.Z_Y_OFFSET_RATIO*radiusY0_;
				zyOffset_=-1*Chip.Z_X_OFFSET_RATIO*radiusX0_;
			} else if (rotation_==180||rotation_==-180) {
				zxOffset_=-Chip.Z_X_OFFSET_RATIO*radiusX0_;
				zyOffset_=-Chip.Z_Y_OFFSET_RATIO*radiusY0_;	
			} else if (rotation_==90||rotation_==-270) {
				zxOffset_=-1*Chip.Z_Y_OFFSET_RATIO*radiusY0_;
				zyOffset_=Chip.Z_X_OFFSET_RATIO*radiusX0_;
			} else {
				zxOffset_=Chip.Z_X_OFFSET_RATIO*radiusX0_;
				zyOffset_=Chip.Z_Y_OFFSET_RATIO*radiusY0_;	
			}
			renderer.spriteBatch.draw(renderer.shadowTexture,
					thisChip_.x*renderer.shadowDownsampleFactor-radiusX0_+zxOffset_*thisChip_.z,
					thisChip_.y*renderer.shadowDownsampleFactor-radiusY0_+zyOffset_*thisChip_.z,
					radiusX0_*2,radiusY0_*2,
					0,0,32,32,false,false);
			if (thisChip_.pooling)
				numPooling_++;
		}
		return numPooling_;
	}
	

	private void renderPotStackShadow(ChipStack stack_) {
		Arrays.fill(potChipsRendered,0);
		int numStack_=stack_.size();
		int maxNum=stack_.maxRenderNum;
		int N=0;
		float zxOffset_;
		float zyOffset_;
		int radiusX0_=(int) (Chip.radiusX*renderer.shadowDownsampleFactor*1.1f);
		int radiusY0_=(int) (Chip.radiusY*renderer.shadowDownsampleFactor*1.1f);
		for (;N<numStack_;N++) {
			Chip thisChip_=stack_.get(N);
			if (potChipsRendered[thisChip_.destSeat.position]<maxNum) {
				float rotation_=thisChip_.rotation;
				if (rotation_==-90||rotation_==270) {
					zxOffset_=Chip.Z_Y_OFFSET_RATIO*radiusY0_;
					zyOffset_=-1*Chip.Z_X_OFFSET_RATIO*radiusX0_;
				} else if (rotation_==180||rotation_==-180) {
					zxOffset_=-Chip.Z_X_OFFSET_RATIO*radiusX0_;
					zyOffset_=-Chip.Z_Y_OFFSET_RATIO*radiusY0_;	
				} else if (rotation_==90||rotation_==-270) {
					zxOffset_=-1*Chip.Z_Y_OFFSET_RATIO*radiusY0_;
					zyOffset_=Chip.Z_X_OFFSET_RATIO*radiusX0_;
				} else {
					zxOffset_=Chip.Z_X_OFFSET_RATIO*radiusX0_;
					zyOffset_=Chip.Z_Y_OFFSET_RATIO*radiusY0_;	
				}
				renderer.spriteBatch.draw(renderer.shadowTexture,
						thisChip_.x*renderer.shadowDownsampleFactor-radiusX0_+zxOffset_*thisChip_.z,
						thisChip_.y*renderer.shadowDownsampleFactor-radiusY0_+zyOffset_*thisChip_.z,
						radiusX0_*2,radiusY0_*2,
						0,0,32,32,false,false);
				potChipsRendered[thisChip_.destSeat.position]++;
			}
		}
					
	}

	private void renderBackground() {
		float tileDim=renderer.mHS.feltTileDim;
		// draw felt
		for (int x=-1;x<renderer.screenWidth;x+=tileDim) {
			for (int j=0;j<2;j++) {
				renderer.spriteBatch.draw(renderer.feltTexture,x,tileDim*j,tileDim,tileDim,0,0,256,256, false,false);
			}
		}
	}
	
	public void renderShadows() {
		renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,0.23f);
		renderer.spriteBatch.draw(renderer.occluders,0,0,renderer.screenWidth,renderer.screenHeight);
		renderer.alphaShader=renderer.spriteBatch.getColor();
		renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
	}
	
	private void renderChairs() {
		renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,renderer.alphaShader.b,Seat.opacity);
        for (int i=0;i<HostScreen.NUM_SEATS;i++) {
        	if (renderer.mHS.seats[i].rotation==0) {
        		renderer.spriteBatch.draw(renderer.chairTexture,renderer.mHS.seats[i].x-Seat.radiusX,
        				renderer.mHS.seats[i].y-Seat.radiusY,
        				Seat.radiusX*2,Seat.radiusY*2,
        				0,0,202,256,false,false);
        	} else {
        		renderer.spriteBatch.draw(renderer.chairTexture,renderer.mHS.seats[i].x-Seat.radiusX,
        				renderer.mHS.seats[i].y-Seat.radiusY,
        				Seat.radiusX,Seat.radiusY,
        				Seat.radiusX*2,Seat.radiusY*2,1,1,renderer.mHS.seats[i].rotation,
        				0,0,202,256,false,false);
        	}
        }
        renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,renderer.alphaShader.b,1);
        
        if (renderer.mHS.gameStage==HostScreen.STAGE_WAITING_BET) {
        	renderer.alphaShader=renderer.spriteBatch.getColor();
            renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,renderer.alphaShader.b,renderer.mHS.spotlight.opacity);
            renderer.spriteBatch.draw(renderer.tableHighlightTexture,
            		renderer.mHS.spotlight.x-renderer.mHS.spotlight.radiusX,
    				renderer.mHS.spotlight.y-renderer.mHS.spotlight.radiusY,
    				renderer.mHS.spotlight.radiusX,renderer.mHS.spotlight.radiusY,
    				renderer.mHS.spotlight.radiusX*2,renderer.mHS.spotlight.radiusY*2,
    				1,1,renderer.mHS.spotlight.rotation,
    				0,0,512,295,false,false);
            renderer.alphaShader=renderer.spriteBatch.getColor();
            renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,renderer.alphaShader.b,1);
        }
	}
	
	private void renderSpotlightsSplit() {
		renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,renderer.alphaShader.b,renderer.mHS.spotlight.opacity);
		for (int i=0;i<HostScreen.NUM_SEATS;i++) {
			if (renderer.mHS.seats[i].player!=null&&renderer.mHS.seats[i].player.selected) {
				renderer.spriteBatch.draw(renderer.tableHighlightTexture,
						renderer.mHS.seats[i].x-renderer.mHS.spotlight.radiusX,
						renderer.mHS.seats[i].y-renderer.mHS.spotlight.radiusY,
						renderer.mHS.spotlight.radiusX,renderer.mHS.spotlight.radiusY,
						renderer.mHS.spotlight.radiusX*2,renderer.mHS.spotlight.radiusY*2,
						1,1,renderer.mHS.seats[i].rotation,
						0,0,512,295,false,false);
			}
		}
		renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,renderer.alphaShader.b,1);
	}
	
	private void renderPlayers() {
		for (int i=0;i<HostScreen.NUM_SEATS;i++) {
			if (renderer.mHS.seats[i].player!=null&&!renderer.mHS.seats[i].player.requestExit) {
				renderer.alphaShader=renderer.spriteBatch.getColor();
		        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
		        		renderer.alphaShader.b,renderer.mHS.seats[i].player.opacity);
				renderer.spriteBatch.draw(renderer.playerTexture,
        				renderer.mHS.seats[i].player.getX()-renderer.mHS.seats[i].player.radiusX,
        				renderer.mHS.seats[i].player.getY()-renderer.mHS.seats[i].player.radiusY,
        				renderer.mHS.seats[i].player.radiusX,
        				renderer.mHS.seats[i].player.radiusY,
        				renderer.mHS.seats[i].player.radiusX*2,
        				renderer.mHS.seats[i].player.radiusY*2,
        				1,1,renderer.mHS.seats[i].rotation,
        				0,0,128,256,false,false);
				if (renderer.mHS.gameStage==HostScreen.STAGE_LOBBY) {
					renderer.spriteBatch.end();
					renderer.mHS.activity.getITextRenderer().renderPlayerName(i);
					renderer.spriteBatch.begin();
				}
			}
        }
	}
	
	private void renderWinStacks() {
		for (int seat=0;seat<HostScreen.NUM_SEATS;seat++) {
			if (renderer.mHS.seats[seat].player!=null) {
				ChipStack stack_=renderer.mHS.seats[seat].player.winStack;
				float rotation_=stack_.getRotation();
				int xCoeff_=0;
				int yCoeff_=0;
				if (rotation_==0||rotation_==360) {
					yCoeff_=1;
				} else if (rotation_==-90||rotation_==270) {
					xCoeff_=1;
				} else if (rotation_==180||rotation_==-180) {
					yCoeff_=-1;
				} else if (rotation_==90||rotation_==-270) {
					xCoeff_=-1;
				}
				renderer.alphaShader=renderer.spriteBatch.getColor();
		        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
		        		renderer.alphaShader.b,stack_.getOpacity());
				for (int i=0;i<stack_.renderSize();i++) {
					Chip chip_=stack_.get(i);
					float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
					float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
					float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
							(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
					renderer.spriteBatch.draw(renderer.chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
							chip_.x-radiusXChip_+xCoeff_*zyOffset_,
							chip_.y-radiusYChip_+yCoeff_*zyOffset_,
							radiusXChip_,radiusYChip_,
							radiusXChip_*2,radiusYChip_*2,
							1,1,rotation_,
							0,0,256,chip_img_height,false,false);
				}
				renderer.alphaShader=renderer.spriteBatch.getColor();
		        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
		        		renderer.alphaShader.b,1);
			}
		}		
	}
	
	private void renderBetStacks() {
		for (int i=0;i<HostScreen.NUM_SEATS;i++) {
			int seat=Seat.zOrder[i];
			if (renderer.mHS.seats[seat].player!=null) {
				ChipStack stack_=renderer.mHS.seats[seat].player.betStack;
				int maxRenNum=renderer.mHS.seats[seat].player.betStack.maxRenderNum;
				int numStack=stack_.renderSize();
				int numPooling=0;
				int chipIndex_=0;
				int topChip_=-1;
				for (int j=numStack-1;j>=0;j--) {
					if (!renderer.mHS.seats[seat].player.betStack.get(j).pooling) {
						topChip_=j;
						break;
					}
				}
				renderer.alphaShader=renderer.spriteBatch.getColor();
		        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
		        		renderer.alphaShader.b,stack_.getOpacity());
				for (;chipIndex_<numStack;chipIndex_++) {
					Chip chip_=stack_.get(chipIndex_);
					float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
					float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
					float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
							(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
					float rotation_=chip_.rotation;
					int xCoeff_=0;
					int yCoeff_=0;
					if (rotation_==0||rotation_==360) {
						yCoeff_=1;
					} else if (rotation_==-90||rotation_==270) {
						xCoeff_=1;
					} else if (rotation_==180||rotation_==-180) {
						yCoeff_=-1;
					} else if (rotation_==90||rotation_==-270) {
						xCoeff_=-1;
					}
					renderer.spriteBatch.draw(renderer.chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
							chip_.x-radiusXChip_+xCoeff_*zyOffset_,
							chip_.y-radiusYChip_+yCoeff_*zyOffset_,
							radiusXChip_,radiusYChip_,
							radiusXChip_*2,radiusYChip_*2,
							1,1,rotation_,
							0,0,256,chip_img_height,false,false);
					if (chip_.pooling)
						numPooling++;
					if (chipIndex_==topChip_) {
						renderer.spriteBatch.end();
						renderer.mHS.activity.getITextRenderer().renderBetTotal(seat,chip_.x,chip_.getProjectedY());
						renderer.spriteBatch.begin();
					}
				}
				// make sure max render num of chips in the pool stack are drawn
				if (renderer.mHS.gameStage==HostScreen.STAGE_WAIT_POOL_STACKS) {
					for (;chipIndex_<stack_.size()&&numPooling<maxRenNum;chipIndex_++) {
						Chip chip_=stack_.get(chipIndex_);
						if (chip_.pooling) {
							float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
							float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
							float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
									(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
							renderer.spriteBatch.draw(renderer.chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
									chip_.x-radiusXChip_,
									chip_.y-radiusYChip_+zyOffset_,
									radiusXChip_*2,radiusYChip_*2,
									0,0,256,chip_img_height,false,false);
							numPooling++;
						}
					}
				}
			}
		}
		renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
	}
	
	private void renderBettingStacks() {
		for (int seat=0;seat<HostScreen.NUM_SEATS;seat++) {
			if (renderer.mHS.seats[seat].player!=null) {
				ChipStack stack_=renderer.mHS.seats[seat].player.bettingStack;
				float rotation_=stack_.getRotation();
				int xCoeff_=0;
				int yCoeff_=0;
				if (rotation_==0||rotation_==360) {
					yCoeff_=1;
				} else if (rotation_==-90||rotation_==270) {
					xCoeff_=1;
				} else if (rotation_==180||rotation_==-180) {
					yCoeff_=-1;
				} else if (rotation_==90||rotation_==-270) {
					xCoeff_=-1;
				}
				renderer.alphaShader=renderer.spriteBatch.getColor();
		        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
		        		renderer.alphaShader.b,stack_.getOpacity());
				for (int chipIndex=0;chipIndex<stack_.renderSize();chipIndex++) {
					Chip chip_=stack_.get(chipIndex);
					float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
					float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
					float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
							(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
					renderer.spriteBatch.draw(renderer.chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
							chip_.x-radiusXChip_+xCoeff_*zyOffset_,
							chip_.y-radiusYChip_+yCoeff_*zyOffset_,
							radiusXChip_,radiusYChip_,
							radiusXChip_*2,radiusYChip_*2,
							1,1,rotation_,
							0,0,256,chip_img_height,false,false);
				}
			}
		}
		renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
	}
	
	private void renderPot() {
		int chipIndex_=0;
		ChipStack stack_=renderer.mHS.pots.get(renderer.mHS.displayedPotIndex).potStack;
		float rotation_=stack_.getRotation();
		int xCoeff_=0;
		int yCoeff_=0;
		if (rotation_==0||rotation_==360) {
			yCoeff_=1;
		} else if (rotation_==-90||rotation_==270) {
			xCoeff_=1;
		} else if (rotation_==180||rotation_==-180) {
			yCoeff_=-1;
		} else if (rotation_==90||rotation_==-270) {
			xCoeff_=-1;
		}
		renderer.alphaShader=renderer.spriteBatch.getColor();		
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,stack_.getOpacity());
		if (renderer.mHS.gameStage==HostScreen.STAGE_SPLITTING_POTS&&stack_.pastMax()) {
			// because chips stay in pot until they reach the seat hence win stacks won't be drawn if above max render num of pot
			int maxNum=stack_.maxRenderNum;
			Arrays.fill(potChipsRendered,0);
			for (chipIndex_=0;chipIndex_<stack_.size();chipIndex_++) {
				Chip chip_=stack_.get(chipIndex_);
				float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
				float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
				float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
						(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
				if (potChipsRendered[chip_.destSeat.position]<maxNum) {
					renderer.spriteBatch.draw(renderer.chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
							chip_.x-radiusXChip_+xCoeff_*zyOffset_,
							chip_.y-radiusYChip_+yCoeff_*zyOffset_,
							radiusXChip_,radiusYChip_,
							radiusXChip_*2,radiusYChip_*2,
							1,1,rotation_,
							0,0,256,chip_img_height,false,false);
					potChipsRendered[chip_.destSeat.position]++;
				}
			}
		} else {
			
			for (chipIndex_=0;chipIndex_<stack_.renderSize();chipIndex_++) {
				Chip chip_=stack_.get(chipIndex_);
				float radiusXChip_=Chip.radiusX*(1+chip_.z*Chip.perspectiveGradient);
				float radiusYChip_=Chip.radiusY*(1+chip_.z*Chip.perspectiveGradient);
				float zyOffset_=Chip.Z_Y_OFFSET_RATIO*Chip.radiusY*chip_.z*
						(1+0.5f*Chip.perspectiveGradient*(chip_.z-1));
				renderer.spriteBatch.draw(renderer.chipTextures[chip_.chipType*Chip.CHIP_ROTATION_N+chip_.imgRotation],
						chip_.x-radiusXChip_+xCoeff_*zyOffset_,
						chip_.y-radiusYChip_+yCoeff_*zyOffset_,
						radiusXChip_,radiusYChip_,
						radiusXChip_*2,radiusYChip_*2,
						1,1,rotation_,
						0,0,256,chip_img_height,false,false);
			}
			
		}
		renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
	}
	
	private void renderButtons() {
		renderer.alphaShader=renderer.spriteBatch.getColor();
		
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,renderer.mHS.splitButton.opacity);
        renderer.spriteBatch.draw(renderer.splitTexture,
				renderer.mHS.splitButton.x-renderer.mHS.splitButton.radiusX,
				renderer.mHS.splitButton.y-renderer.mHS.splitButton.radiusY,
				renderer.mHS.splitButton.radiusX*2,
				renderer.mHS.splitButton.radiusY*2,
				0,0,256,128,false,false);
        renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
        
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,renderer.mHS.cancelSplitButton.opacity);
        renderer.spriteBatch.draw(renderer.cancelSplitTexture,
				renderer.mHS.cancelSplitButton.x-renderer.mHS.cancelSplitButton.radiusX,
				renderer.mHS.cancelSplitButton.y-renderer.mHS.cancelSplitButton.radiusY,
				renderer.mHS.cancelSplitButton.radiusX*2,
				renderer.mHS.cancelSplitButton.radiusY*2,
				0,0,128,128,false,false);
        renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
        
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,renderer.mHS.splitDoneButton.opacity);
        renderer.spriteBatch.draw(renderer.doneSplitTexture,
				renderer.mHS.splitDoneButton.x-renderer.mHS.splitDoneButton.radiusX,
				renderer.mHS.splitDoneButton.y-renderer.mHS.splitDoneButton.radiusY,
				renderer.mHS.splitDoneButton.radiusX*2,
				renderer.mHS.splitDoneButton.radiusY*2,
				0,0,128,128,false,false);
        renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
        
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,renderer.mHS.arrowRight.opacity);
        renderer.spriteBatch.draw(renderer.arrowTexture,
				renderer.mHS.arrowRight.x-renderer.mHS.arrowRight.radiusX,
				renderer.mHS.arrowRight.y-renderer.mHS.arrowRight.radiusY,
				renderer.mHS.arrowRight.radiusX*2,
				renderer.mHS.arrowRight.radiusY*2,
				0,0,128,128,false,false);
        renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,renderer.mHS.arrowLeft.opacity);
        renderer.spriteBatch.draw(renderer.arrowTexture,
				renderer.mHS.arrowLeft.x-renderer.mHS.arrowLeft.radiusX,
				renderer.mHS.arrowLeft.y-renderer.mHS.arrowLeft.radiusY,
				renderer.mHS.arrowLeft.radiusX,
				renderer.mHS.arrowLeft.radiusY,
				renderer.mHS.arrowLeft.radiusX*2,
				renderer.mHS.arrowLeft.radiusY*2,
				1,1,180,
				0,0,128,128,false,false);
        renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
        
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,renderer.mHS.resumeButton.opacity);
        renderer.spriteBatch.draw(renderer.doneSplitTexture,
				renderer.mHS.resumeButton.x-renderer.mHS.resumeButton.radiusX,
				renderer.mHS.resumeButton.y-renderer.mHS.resumeButton.radiusY,
				renderer.mHS.resumeButton.radiusX*2,
				renderer.mHS.resumeButton.radiusY*2,
				0,0,128,128,false,false);
        renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
	}
	
	private void renderMenuTable() {
		renderer.spriteBatch.draw(renderer.menuTableTexture,renderer.mHS.menuTable.x-renderer.mHS.menuTable.radiusX,
				renderer.mHS.menuTable.getY()-renderer.mHS.menuTable.radiusY,
				renderer.mHS.menuTable.radiusX*2,renderer.mHS.menuTable.radiusY*2,
				0,0,720,356,false,false);
		int currentScreen_=renderer.mHS.menuTable.getCurrentScreen();
		if (renderer.mHS.menuTable.isOpen&&(currentScreen_==HostMenuTable.SCREEN_MAIN||
				currentScreen_==HostMenuTable.SCREEN_NO_WIFI)) {
			renderer.spriteBatch.draw(renderer.menuTableLineTexture,
					renderer.mHS.menuTable.linesX1,renderer.mHS.menuTable.linesY3,
					renderer.mHS.menuTable.linesX4-renderer.mHS.menuTable.linesX1,renderer.mHS.menuTable.lineWidth,
					0,0,16,4,false,false);
			renderer.spriteBatch.draw(renderer.menuTableLineTexture,
					renderer.mHS.menuTable.linesX1,renderer.mHS.menuTable.linesY2,
					renderer.mHS.menuTable.linesX4-renderer.mHS.menuTable.linesX1,renderer.mHS.menuTable.lineWidth,
					0,0,16,4,false,false);
			renderer.spriteBatch.draw(renderer.menuTableLineVTexture,
					renderer.mHS.menuTable.linesX2,renderer.mHS.menuTable.linesY3+renderer.mHS.menuTable.lineWidth*0.75f,
					renderer.mHS.menuTable.lineWidth,renderer.mHS.menuTable.linesY4-renderer.mHS.menuTable.linesY3,
					0,0,4,16,false,false);
			renderer.spriteBatch.draw(renderer.menuTableLineVTexture,
					renderer.mHS.menuTable.linesX3,renderer.mHS.menuTable.linesY3+renderer.mHS.menuTable.lineWidth*0.75f,
					renderer.mHS.menuTable.lineWidth,renderer.mHS.menuTable.linesY4-renderer.mHS.menuTable.linesY3,
					0,0,4,16,false,false);
			renderer.spriteBatch.draw(renderer.menuTableLineVTexture,
					renderer.mHS.menuTable.linesX2,renderer.mHS.menuTable.linesY1+renderer.mHS.menuTable.lineWidth*0.25f,
					renderer.mHS.menuTable.lineWidth,renderer.mHS.menuTable.linesY2-renderer.mHS.menuTable.linesY1,
					0,0,4,16,false,false);
			renderer.spriteBatch.draw(renderer.menuTableLineVTexture,
					renderer.mHS.menuTable.linesX3,renderer.mHS.menuTable.linesY1+renderer.mHS.menuTable.lineWidth*0.25f,
					renderer.mHS.menuTable.lineWidth,renderer.mHS.menuTable.linesY2-renderer.mHS.menuTable.linesY1,
					0,0,4,16,false,false);
			if (currentScreen_==HostMenuTable.SCREEN_MAIN) {
				renderer.spriteBatch.draw(renderer.hostTexture,
						renderer.mHS.menuTable.reseatButton.x-renderer.mHS.menuTable.reseatButton.radiusX,
						renderer.mHS.menuTable.reseatButton.y-renderer.mHS.menuTable.reseatButton.radiusY,
						renderer.mHS.menuTable.reseatButton.radiusX*2,
						renderer.mHS.menuTable.reseatButton.radiusY*2,
						0,0,256,256,false,false);
				renderer.spriteBatch.draw(renderer.hostTexture,
						renderer.mHS.menuTable.bootButton.x-renderer.mHS.menuTable.bootButton.radiusX,
						renderer.mHS.menuTable.bootButton.y-renderer.mHS.menuTable.bootButton.radiusY,
						renderer.mHS.menuTable.bootButton.radiusX*2,
						renderer.mHS.menuTable.bootButton.radiusY*2,
						0,0,256,256,false,false);
				renderer.spriteBatch.draw(renderer.hostTexture,
						renderer.mHS.menuTable.destroyButton.x-renderer.mHS.menuTable.destroyButton.radiusX,
						renderer.mHS.menuTable.destroyButton.y-renderer.mHS.menuTable.destroyButton.radiusY,
						renderer.mHS.menuTable.destroyButton.radiusX*2,
						renderer.mHS.menuTable.destroyButton.radiusY*2,
						0,0,256,256,false,false);
				renderer.spriteBatch.draw(renderer.hostTexture,
						renderer.mHS.menuTable.undoButton.x-renderer.mHS.menuTable.undoButton.radiusX,
						renderer.mHS.menuTable.undoButton.y-renderer.mHS.menuTable.undoButton.radiusY,
						renderer.mHS.menuTable.undoButton.radiusX*2,
						renderer.mHS.menuTable.undoButton.radiusY*2,
						0,0,256,256,false,false);
			} else if (currentScreen_==HostMenuTable.SCREEN_NO_WIFI) {
				renderer.spriteBatch.draw(renderer.settingsTexture,
						renderer.mHS.menuTable.settingsButton.x-renderer.mHS.menuTable.settingsButton.radiusX,
						renderer.mHS.menuTable.settingsButton.y-renderer.mHS.menuTable.settingsButton.radiusY,
						renderer.mHS.menuTable.settingsButton.radiusX*2,
						renderer.mHS.menuTable.settingsButton.radiusY*2,
						0,0,128,128,false,false);
			}
		}
		
	}
	
	public void renderDealerChip() {
		renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,renderer.mHS.dealerChip.opacity);
		if (renderer.mHS.dealerChip.rotation==0) {
    		renderer.spriteBatch.draw(renderer.dealerChipTexture,
    				renderer.mHS.dealerChip.x-renderer.mHS.dealerChip.radiusX,
    				renderer.mHS.dealerChip.y-renderer.mHS.dealerChip.radiusY,
    				renderer.mHS.dealerChip.radiusX*2,
    				renderer.mHS.dealerChip.radiusY*2,
    				0,0,256,256,false,false);
    	} else {
    		renderer.spriteBatch.draw(renderer.dealerChipTexture,
    				renderer.mHS.dealerChip.x-renderer.mHS.dealerChip.radiusX,
    				renderer.mHS.dealerChip.y-renderer.mHS.dealerChip.radiusY,
    				renderer.mHS.dealerChip.radiusX,
    				renderer.mHS.dealerChip.radiusY,
    				renderer.mHS.dealerChip.radiusX*2,
    				renderer.mHS.dealerChip.radiusY*2,
    				1,1,renderer.mHS.dealerChip.rotation,
    				0,0,256,256,false,false);
    	}
		renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
	}
	
	private void renderFlingDemo() {
		renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,renderer.mHS.flingDemo.hand.opacity);
        
        renderer.spriteBatch.draw(renderer.handTexture,
				renderer.mHS.flingDemo.hand.x-renderer.mHS.flingDemo.hand.radiusX,
				renderer.mHS.flingDemo.hand.y-renderer.mHS.flingDemo.hand.radiusY,
				renderer.mHS.flingDemo.hand.radiusX,
				renderer.mHS.flingDemo.hand.radiusY,
				renderer.mHS.flingDemo.hand.radiusX*2,
				renderer.mHS.flingDemo.hand.radiusY*2,
				1,1,renderer.mHS.flingDemo.rotation,
				0,0,128,128,false,false);
        
        renderer.alphaShader=renderer.spriteBatch.getColor();
        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
        		renderer.alphaShader.b,1);
	}
	
	private void renderForegroundPlayers() {
		if (renderer.mHS.pickedUpPlayer!=null) {
			if (renderer.mHS.pickedUpPlayer.getRotation()==0) {
        		renderer.spriteBatch.draw(renderer.playerTexture,
        				renderer.mHS.pickedUpPlayer.getX()-renderer.mHS.pickedUpPlayer.radiusX,
        				renderer.mHS.pickedUpPlayer.getY()-renderer.mHS.pickedUpPlayer.radiusY,
        				renderer.mHS.pickedUpPlayer.radiusX*2,
        				renderer.mHS.pickedUpPlayer.radiusY*2,
        				0,0,128,256,false,false);
        	} else {
        		renderer.spriteBatch.draw(renderer.playerTexture,
        				renderer.mHS.pickedUpPlayer.getX()-renderer.mHS.pickedUpPlayer.radiusX,
        				renderer.mHS.pickedUpPlayer.getY()-renderer.mHS.pickedUpPlayer.radiusY,
        				renderer.mHS.pickedUpPlayer.radiusX,
        				renderer.mHS.pickedUpPlayer.radiusY,
        				renderer.mHS.pickedUpPlayer.radiusX*2,
        				renderer.mHS.pickedUpPlayer.radiusY*2,
        				1,1,renderer.mHS.pickedUpPlayer.getRotation(),
        				0,0,128,256,false,false);
        	}
			renderer.spriteBatch.end();
			renderer.mHS.activity.getITextRenderer().renderPickedUpPlayerName();
			renderer.spriteBatch.begin();
		}
		if (renderer.mHS.swapPlayer!=null) {
			if (renderer.mHS.swapPlayer.getRotation()==0) {
        		renderer.spriteBatch.draw(renderer.playerTexture,
        				renderer.mHS.swapPlayer.getX()-renderer.mHS.swapPlayer.radiusX,
        				renderer.mHS.swapPlayer.getY()-renderer.mHS.swapPlayer.radiusY,
        				renderer.mHS.swapPlayer.radiusX*2,
        				renderer.mHS.swapPlayer.radiusY*2,
        				0,0,128,256,false,false);
        	} else {
        		renderer.spriteBatch.draw(renderer.playerTexture,
        				renderer.mHS.swapPlayer.getX()-renderer.mHS.swapPlayer.radiusX,
        				renderer.mHS.swapPlayer.getY()-renderer.mHS.swapPlayer.radiusY,
        				renderer.mHS.swapPlayer.radiusX,
        				renderer.mHS.swapPlayer.radiusY,
        				renderer.mHS.swapPlayer.radiusX*2,
        				renderer.mHS.swapPlayer.radiusY*2,
        				1,1,renderer.mHS.swapPlayer.getRotation(),
        				0,0,128,256,false,false);
        	}
			renderer.spriteBatch.end();
			renderer.mHS.activity.getITextRenderer().renderSwapPlayerName();
			renderer.spriteBatch.begin();
		}
	}
	
	private void renderFadeIn() {
        if (renderer.mHS.screenOpacity<1) {
        	renderer.alphaShader=renderer.spriteBatch.getColor();
	        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,
	        		renderer.alphaShader.b,1-renderer.mHS.screenOpacity);
	        renderer.spriteBatch.draw(renderer.blackRectangleTexture,renderer.cameraX,renderer.cameraY);
	        renderer.alphaShader=renderer.spriteBatch.getColor();
	        renderer.spriteBatch.setColor(renderer.alphaShader.r,renderer.alphaShader.g,renderer.alphaShader.b,1);
		}
	}
	
	public void dispose() {
		renderer=null;
	}
	*/
}
