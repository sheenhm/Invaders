package entity;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

import engine.*;
import engine.DrawManager.SpriteType;

/**
 * Implements a enemy ship, to be destroyed by the player.
 * Code Refactor Completed. (EnemyShip A, EnemyShip B, EnemyShip C -> EnemyShip)
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class EnemyShip extends Entity {

	/** Point value of a bonus enemy. */
	private static final int BONUS_TYPE_POINTS = 100;

	public static final double ITEM_PROPORTION = 0.1;
	public static final int RANDOM_BOUND = 10000;
	/** Cooldown between sprite changes. */
	protected Cooldown animationCooldown;
	/** Checks if the ship has been hit by a bullet. */
	private boolean isDestroyed;
	/** 난이도 조절에 사용할 현재 스테이트 */
	private GameState gameState;
	/** Values of the ship, in points, when destroyed. */
	protected int pointValue;
	private boolean hasItem;
	private int itemRange;
	protected int HP;
	protected static final int BULLET_SPEED = 4;

	/** 총알의 속도 배율 */
	private double bulletSpeedPower;
	/** 슈팅 쿨다운 배율 */
	private double bulletCooldown;

	/**
	 * Constructor, establishes the ship's properties.
	 *
	 * @param positionX  Initial position of the ship in the X axis.
	 * @param positionY  Initial position of the ship in the Y axis.
	 * @param spriteType Sprite type, image corresponding to the ship.
	 */
	public EnemyShip(final int positionX, final int positionY,
					 final SpriteType spriteType, final GameState gameState,
					 final double hpPower, final double bulletSpeedPower, final double bulletCooldown, final int point) {
		super(positionX, positionY, 12 * 2, 8 * 2, Color.WHITE);
		this.gameState = gameState;
		this.bulletSpeedPower = bulletSpeedPower;
		this.bulletCooldown = bulletCooldown;
		this.pointValue = point;
		this.spriteType = spriteType;
		this.animationCooldown = Core.getCooldown(500);
		this.isDestroyed = false;
		this.itemRange =  new Random().nextInt(RANDOM_BOUND);
		this.hasItem = itemGenerator(itemRange);
		this.HP = (int)(this.gameState.getLevel() * hpPower);
	}

	/**
	 * Constructor, establishes the ship's properties for a special ship, with
	 * known starting properties.
	 */
	public EnemyShip() {
		super(-32, 60, 16 * 2, 7 * 2, Color.RED);

		this.spriteType = SpriteType.EnemyShipSpecial;
		this.isDestroyed = false;
		this.pointValue = BONUS_TYPE_POINTS;
	}

	/**
	 * Getter for the score bonus if this ship is destroyed.
	 * 
	 * @return Value of the ship.
	 */
	public final int getPointValue() {
		return this.pointValue;
	}

	/**
	 * Moves the ship the specified distance.
	 * 
	 * @param distanceX
	 *            Distance to move in the X axis.
	 * @param distanceY
	 *            Distance to move in the Y axis.
	 */
	public final void move(final int distanceX, final int distanceY) {
		this.positionX += distanceX;
		this.positionY += distanceY;
	}

	public final void shoot(final Set<Bullet> bullets, Cooldown shootingCooldown) {
		bullets.add(BulletPool.getBullet(positionX
				+ width / 2, positionY, (int)(BULLET_SPEED * bulletSpeedPower),0));
		shootingCooldown.timedown(bulletCooldown);
	}

	/**
	 * Destroys the ship, causing an explosion.
	 */
	public final void destroy() {
		this.HP--;
		if (this.HP <= 0) {
			SoundManager.playSound("SFX/S_Enemy_Destroy_a", "Enemy_destroyed", false, false);
			this.isDestroyed = true;
			this.spriteType = SpriteType.Explosion;
		}
	}

	public final void destroyByBomb(){
		this.HP = 0;
		this.isDestroyed = true;
		this.spriteType = SpriteType.Explosion;
	}
	/**
	 * Checks if the ship has been destroyed.
	 * 
	 * @return True if the ship has been destroyed.
	 */
	public final boolean isDestroyed() {return this.isDestroyed;}
	public final int getpositionY() { return this.positionY; }


	/**
	 * 랜덤으로 Item을 가진 EnemyShip 생성*/
	private boolean itemGenerator(int rand_int){
		if(rand_int < (int)(RANDOM_BOUND * ITEM_PROPORTION))
			return true;
		else
			return false;
	}

	/** EnemyShip이 아이템을 지닌 객체인지 확인 */
	public final boolean hasItem(){
		return this.hasItem;
	}

	public int getItemRange(){return this.itemRange;}

	public final void update(SpriteType spriteType) {
		if (this.animationCooldown.checkFinished()) {
			this.animationCooldown.reset();

			if (spriteType == SpriteType.EnemyShipA1) {
				this.spriteType = SpriteType.EnemyShipA2;
			} else if (spriteType == SpriteType.EnemyShipB1) {
				this.spriteType = SpriteType.EnemyShipB2;
			}
		}
	}
}