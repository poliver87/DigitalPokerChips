package com.bidjee.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.NumberUtils;

public class ChipBatch implements Disposable {
	private Mesh mesh;

	private Texture lastTexture = null;
	private float invTexWidth = 0;
	private float invTexHeight = 0;

	private int idx = 0;
	private final float[] vertices;

	private final Matrix4 transformMatrix = new Matrix4();
	private final Matrix4 projectionMatrix = new Matrix4();
	private final Matrix4 combinedMatrix = new Matrix4();

	private boolean drawing = false;

	private boolean blendingDisabled = false;
	private int blendSrcFunc = GL11.GL_SRC_ALPHA;
	private int blendDstFunc = GL11.GL_ONE_MINUS_SRC_ALPHA;

	private final ShaderProgram shader;

	float color = Color.WHITE.toFloatBits();
	private Color tempColor = new Color(1, 1, 1, 1);

	/** number of render calls since last {@link #begin()} **/
	public int renderCalls = 0;

	/** number of rendering calls ever, will not be reset, unless it's done manually **/
	public int totalRenderCalls = 0;

	/** the maximum number of sprites rendered in one batch so far **/
	public int maxSpritesInBatch = 0;

	public ChipBatch() {
		int size=2000;
		mesh = new Mesh(VertexDataType.VertexArray, false, size * 4, size * 6, new VertexAttribute(Usage.Position, 2,
			ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
			new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + "0")
			,new VertexAttribute(Usage.Generic,2,"a_shadowPosition")
			,new VertexAttribute(Usage.Generic,1,"a_shadowRadius")
			);

		projectionMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		vertices = new float[size * (8)*4];

		int len = size * 6;
		short[] indices = new short[len];
		short j = 0;
		for (int i = 0; i < len; i += 6, j += 4) {
			indices[i + 0] = (short)(j + 0);
			indices[i + 1] = (short)(j + 1);
			indices[i + 2] = (short)(j + 2);
			indices[i + 3] = (short)(j + 2);
			indices[i + 4] = (short)(j + 3);
			indices[i + 5] = (short)(j + 0);
		}
		mesh.setIndices(indices);

		shader = createShader();
	}
	
	static public ShaderProgram createShader () {
		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "attribute vec2 a_shadowPosition;\n" //
			+ "attribute float a_shadowRadius;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ "varying vec4 v_position;\n" //
			+ "varying vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "varying vec2 v_shadowPosition;\n" //
			+ "varying float v_shadowRadius;\n" //
			+ "\n" //
			+ "void main()\n" //
			+ "{\n" //
			+ "   v_position = " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "   v_shadowPosition = a_shadowPosition;\n" //
			+ "   v_shadowRadius = a_shadowRadius;\n" //
			+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "}\n";
		String fragmentShader = "#ifdef GL_ES\n" //
			+ "#define LOWP lowp\n" //
			+ "precision mediump float;\n" //
			+ "#else\n" //
			+ "#define LOWP \n" //
			+ "#endif\n" //
			+ "varying vec4 v_position;\n" //
			+ "varying LOWP vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "varying vec2 v_shadowPosition;\n" //
			+ "varying float v_shadowRadius;\n" //
			+ "uniform sampler2D u_texture;\n" //
			+ "void main()\n"//
			+ "{\n" //
			+ "  vec4 texColor = texture2D(u_texture, v_texCoords);" //
			+ "  float dist = length(v_position.xy-v_shadowPosition.xy);\n"
			+ "  float tint = 1.0-0.2*smoothstep(dist*0.95,dist*1.05,v_shadowRadius);\n" //
			+ "  texColor.xyz*=tint;\n" //
			+ "  gl_FragColor = v_color * texColor;\n" //
			+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) throw new IllegalArgumentException("couldn't compile shader: " + shader.getLog());
		return shader;
	}

	/** Sets up the SpriteBatch for drawing. This will disable depth buffer writting. It enables blending and texturing. If you have
	 * more texture units enabled than the first one you have to disable them before calling this. Uses a screen coordinate system
	 * by default where everything is given in pixels. You can specify your own projection and modelview matrices via
	 * {@link #setProjectionMatrix(Matrix4)} and {@link #setTransformMatrix(Matrix4)}. */
	public void begin () {
		if (drawing) throw new IllegalStateException("you have to call SpriteBatch.end() first");
		renderCalls = 0;

		Gdx.gl.glDepthMask(false);
		shader.begin();
		setupMatrices();

		idx = 0;
		lastTexture = null;
		drawing = true;
	}

	/** Finishes off rendering. Enables depth writes, disables blending and texturing. Must always be called after a call to
	 * {@link #begin()} */
	public void end () {
		if (!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before end.");
		if (idx > 0) renderMesh();
		lastTexture = null;
		idx = 0;
		drawing = false;

		GLCommon gl = Gdx.gl;
		gl.glDepthMask(true);
		if (isBlendingEnabled()) gl.glDisable(GL10.GL_BLEND);
		
		shader.end();
	}

	public void setColor (Color tint) {
		color = tint.toFloatBits();
	}

	public void setColor (float r, float g, float b, float a) {
		int intBits = (int)(255 * a) << 24 | (int)(255 * b) << 16 | (int)(255 * g) << 8 | (int)(255 * r);
		color = NumberUtils.intToFloatColor(intBits);
	}

	public void setColor (float color) {
		this.color = color;
	}

	public Color getColor () {
		int intBits = NumberUtils.floatToIntColor(color);
		Color color = this.tempColor;
		color.r = (intBits & 0xff) / 255f;
		color.g = ((intBits >>> 8) & 0xff) / 255f;
		color.b = ((intBits >>> 16) & 0xff) / 255f;
		color.a = ((intBits >>> 24) & 0xff) / 255f;
		return color;
	}

	public void draw (Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth,
		int srcHeight, float shadowX, float shadowY,float shadowRadius) {
		if (!drawing) throw new IllegalStateException("SpriteBatch.begin must be called before draw.");

		if (texture != lastTexture) {
			switchTexture(texture);
		} else if (idx == vertices.length) {
			renderMesh();
		}

		float u = srcX * invTexWidth;
		float v = (srcY + srcHeight) * invTexHeight;
		float u2 = (srcX + srcWidth) * invTexWidth;
		float v2 = srcY * invTexHeight;
		final float fx2 = x + width;
		final float fy2 = y + height;

		vertices[idx++] = x;
		vertices[idx++] = y;
		vertices[idx++] = color;
		vertices[idx++] = u;
		vertices[idx++] = v;
		vertices[idx++] = shadowX;
		vertices[idx++] = shadowY;
		vertices[idx++] = shadowRadius;

		vertices[idx++] = x;
		vertices[idx++] = fy2;
		vertices[idx++] = color;
		vertices[idx++] = u;
		vertices[idx++] = v2;
		vertices[idx++] = shadowX;
		vertices[idx++] = shadowY;
		vertices[idx++] = shadowRadius;

		vertices[idx++] = fx2;
		vertices[idx++] = fy2;
		vertices[idx++] = color;
		vertices[idx++] = u2;
		vertices[idx++] = v2;
		vertices[idx++] = shadowX;
		vertices[idx++] = shadowY;
		vertices[idx++] = shadowRadius;

		vertices[idx++] = fx2;
		vertices[idx++] = y;
		vertices[idx++] = color;
		vertices[idx++] = u2;
		vertices[idx++] = v;
		vertices[idx++] = shadowX;
		vertices[idx++] = shadowY;
		vertices[idx++] = shadowRadius;
	}


	private void renderMesh () {
		if (idx == 0) return;

		renderCalls++;
		totalRenderCalls++;
		int spritesInBatch = idx / 32;
		if (spritesInBatch > maxSpritesInBatch) maxSpritesInBatch = spritesInBatch;

		lastTexture.bind();
		mesh.setVertices(vertices, 0, idx);
		mesh.getIndicesBuffer().position(0);
		mesh.getIndicesBuffer().limit(spritesInBatch * 6);

		if (blendingDisabled) {
			Gdx.gl.glDisable(GL20.GL_BLEND);
		} else {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			if (blendSrcFunc != -1) Gdx.gl.glBlendFunc(blendSrcFunc, blendDstFunc);
		}

		mesh.render(shader, GL10.GL_TRIANGLES, 0, spritesInBatch * 6);

		idx = 0;
	}

	public void disableBlending () {
		if (blendingDisabled) return;
		renderMesh();
		blendingDisabled = true;
	}

	public void enableBlending () {
		if (!blendingDisabled) return;
		renderMesh();
		blendingDisabled = false;
	}

	public void setBlendFunction (int srcFunc, int dstFunc) {
		renderMesh();
		blendSrcFunc = srcFunc;
		blendDstFunc = dstFunc;
	}

	public void dispose () {
		mesh.dispose();
		shader.dispose();
	}

	public void setProjectionMatrix (Matrix4 projection) {
		if (drawing) renderMesh();
		projectionMatrix.set(projection);
		if (drawing) setupMatrices();
	}

	public void setTransformMatrix (Matrix4 transform) {
		if (drawing) renderMesh();
		transformMatrix.set(transform);
		if (drawing) setupMatrices();
	}

	private void setupMatrices () {
		combinedMatrix.set(projectionMatrix).mul(transformMatrix);
		shader.setUniformMatrix("u_projTrans", combinedMatrix);
		shader.setUniformi("u_texture", 0);
	}

	private void switchTexture (Texture texture) {
		renderMesh();
		lastTexture = texture;
		invTexWidth = 1.0f / texture.getWidth();
		invTexHeight = 1.0f / texture.getHeight();
	}
	
	public boolean isBlendingEnabled () {
		return !blendingDisabled;
	}
}

