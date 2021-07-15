# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# This file is the source Rails uses to define your schema when running `bin/rails
# db:schema:load`. When creating a new database, `bin/rails db:schema:load` tends to
# be faster and is potentially less error prone than running all of your
# migrations from scratch. Old migrations may fail to apply correctly if those
# migrations use external dependencies or application code.
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 2021_07_15_090830) do

  create_table "countries", force: :cascade do |t|
    t.string "name"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
  end

  create_table "impressions", force: :cascade do |t|
    t.string "impressionable_type"
    t.integer "impressionable_id"
    t.integer "user_id"
    t.string "controller_name"
    t.string "action_name"
    t.string "view_name"
    t.string "request_hash"
    t.string "ip_address"
    t.string "session_hash"
    t.text "message"
    t.text "referrer"
    t.text "params"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["controller_name", "action_name", "ip_address"], name: "controlleraction_ip_index"
    t.index ["controller_name", "action_name", "request_hash"], name: "controlleraction_request_index"
    t.index ["controller_name", "action_name", "session_hash"], name: "controlleraction_session_index"
    t.index ["impressionable_type", "impressionable_id", "ip_address"], name: "poly_ip_index"
    t.index ["impressionable_type", "impressionable_id", "params"], name: "poly_params_request_index"
    t.index ["impressionable_type", "impressionable_id", "request_hash"], name: "poly_request_index"
    t.index ["impressionable_type", "impressionable_id", "session_hash"], name: "poly_session_index"
    t.index ["impressionable_type", "message", "impressionable_id"], name: "impressionable_type_message_index"
    t.index ["user_id"], name: "index_impressions_on_user_id"
  end

  create_table "notifications", force: :cascade do |t|
    t.integer "user_id", null: false
    t.string "resource_type", null: false
    t.integer "resource_id", null: false
    t.string "state"
    t.string "kind"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["resource_type", "resource_id"], name: "index_notifications_on_resource_type_and_resource_id"
    t.index ["user_id"], name: "index_notifications_on_user_id"
  end

  create_table "resume_answer_comments", force: :cascade do |t|
    t.integer "resume_id", null: false
    t.integer "answer_id", null: false
    t.integer "user_id", null: false
    t.integer "answer_user_id", null: false
    t.string "content"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["answer_id"], name: "index_resume_answer_comments_on_answer_id"
    t.index ["answer_user_id"], name: "index_resume_answer_comments_on_answer_user_id"
    t.index ["resume_id"], name: "index_resume_answer_comments_on_resume_id"
    t.index ["user_id"], name: "index_resume_answer_comments_on_user_id"
  end

  create_table "resume_answer_likes", force: :cascade do |t|
    t.integer "resume_id", null: false
    t.integer "answer_id", null: false
    t.integer "user_id", null: false
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["answer_id", "user_id"], name: "index_resume_answer_likes_on_answer_id_and_user_id", unique: true
    t.index ["answer_id"], name: "index_resume_answer_likes_on_answer_id"
    t.index ["resume_id"], name: "index_resume_answer_likes_on_resume_id"
    t.index ["user_id"], name: "index_resume_answer_likes_on_user_id"
  end

  create_table "resume_answers", force: :cascade do |t|
    t.integer "resume_id", null: false
    t.integer "user_id", null: false
    t.text "content"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.integer "likes_count"
    t.string "applying_state"
    t.index ["resume_id"], name: "index_resume_answers_on_resume_id"
    t.index ["user_id", "resume_id"], name: "index_resume_answers_on_user_id_and_resume_id", unique: true
    t.index ["user_id"], name: "index_resume_answers_on_user_id"
  end

  create_table "resume_comments", force: :cascade do |t|
    t.integer "resume_id"
    t.integer "user_id"
    t.string "content"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.index ["resume_id"], name: "index_resume_comments_on_resume_id"
    t.index ["user_id"], name: "index_resume_comments_on_user_id"
  end

  create_table "resume_educations", force: :cascade do |t|
    t.integer "resume_id", null: false
    t.string "institution"
    t.string "faculty"
    t.date "begin_date"
    t.date "end_date"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.string "description"
    t.boolean "current"
    t.index ["resume_id"], name: "index_resume_educations_on_resume_id"
  end

  create_table "resume_works", force: :cascade do |t|
    t.integer "resume_id", null: false
    t.string "company"
    t.string "position"
    t.date "begin_date"
    t.date "end_date"
    t.string "description"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.boolean "current"
    t.index ["resume_id"], name: "index_resume_works_on_resume_id"
  end

  create_table "resumes", force: :cascade do |t|
    t.string "state"
    t.string "name"
    t.integer "user_id", null: false
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.string "url"
    t.text "summary"
    t.text "skills_description"
    t.string "github_url"
    t.text "awards_description"
    t.string "english_fluency"
    t.integer "impressions_count", default: 0
    t.integer "answers_count", default: 0, null: false
    t.string "hexlet_url"
    t.string "contact"
    t.index ["user_id"], name: "index_resumes_on_user_id"
  end

  create_table "users", force: :cascade do |t|
    t.string "email"
    t.string "first_name"
    t.string "last_name"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.string "encrypted_password", default: "", null: false
    t.string "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer "sign_in_count", default: 0, null: false
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.string "current_sign_in_ip"
    t.string "last_sign_in_ip"
    t.string "confirmation_token"
    t.datetime "confirmed_at"
    t.datetime "confirmation_sent_at"
    t.string "unconfirmed_email"
    t.integer "failed_attempts", default: 0, null: false
    t.string "unlock_token"
    t.datetime "locked_at"
    t.string "provider"
    t.string "uid"
    t.integer "resume_answer_likes_count", default: 0, null: false
    t.string "about"
    t.boolean "resume_mail_enabled"
    t.boolean "bounced_email"
    t.boolean "marked_as_spam"
    t.boolean "email_disabled_delivery"
    t.string "role"
    t.string "state"
    t.index ["confirmation_token"], name: "index_users_on_confirmation_token", unique: true
    t.index ["email"], name: "index_users_on_email", unique: true
    t.index ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true
    t.index ["unlock_token"], name: "index_users_on_unlock_token", unique: true
  end

  create_table "vacancies", force: :cascade do |t|
    t.integer "creator_id", null: false
    t.string "state"
    t.string "title"
    t.string "language"
    t.string "location"
    t.string "company_name"
    t.string "site"
    t.string "contact_name"
    t.string "contact_telegram"
    t.string "contact_phone"
    t.text "description"
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
    t.string "city_name"
    t.integer "country_id"
    t.string "link_for_contact"
    t.index ["country_id"], name: "index_vacancies_on_country_id"
    t.index ["creator_id"], name: "index_vacancies_on_creator_id"
  end

  create_table "versions", force: :cascade do |t|
    t.string "item_type", null: false
    t.integer "item_id", limit: 8, null: false
    t.string "event", null: false
    t.string "whodunnit"
    t.text "object", limit: 1073741823
    t.datetime "created_at"
    t.index ["item_type", "item_id"], name: "index_versions_on_item_type_and_item_id"
  end

  add_foreign_key "notifications", "users"
  add_foreign_key "resume_answer_comments", "resume_answers", column: "answer_id"
  add_foreign_key "resume_answer_comments", "resumes"
  add_foreign_key "resume_answer_comments", "users"
  add_foreign_key "resume_answer_comments", "users", column: "answer_user_id"
  add_foreign_key "resume_answer_likes", "resume_answers", column: "answer_id"
  add_foreign_key "resume_answer_likes", "resumes"
  add_foreign_key "resume_answer_likes", "users"
  add_foreign_key "resume_answers", "resumes"
  add_foreign_key "resume_answers", "users"
  add_foreign_key "resume_educations", "resumes"
  add_foreign_key "resume_works", "resumes"
  add_foreign_key "resumes", "users"
  add_foreign_key "vacancies", "countries"
  add_foreign_key "vacancies", "users", column: "creator_id"
end
